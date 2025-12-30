package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.CreateOrderDTOIn;
import com.tuwaiq.project_ghars.DTOIn.OrderItemDTOIn;
import com.tuwaiq.project_ghars.DTOIn.PaymentRequestDTOIn;
import com.tuwaiq.project_ghars.DTOout.OrderDTOOut;
import com.tuwaiq.project_ghars.DTOout.OrderItemDTOOut;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentService paymentService;
    private final EmailService emailService;


    public List<OrderDTOOut> getAllOrders(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//    if (!user.getRole().equals("ADMIN"))
//        throw new ApiException("Access denied");

        return orderRepository.findAll().stream()
                .map(order -> new OrderDTOOut(
                        order.getId(),
                        order.getStatus(),
                        order.getTotalPrice(),
                        order.getCreatedAt(),
                        order.getOrderItem().stream()
                                .map(item -> new OrderItemDTOOut(
                                        item.getProduct().getId(),
                                        item.getProduct().getName(),
                                        item.getQuantity(),
                                        item.getLinePrice())).toList())).toList();
    }


    public List<OrderDTOOut> getMyOrders(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//    if (!user.getRole().equals("CUSTOMER"))
//        throw new ApiException("Only customer can access this");

        return orderRepository.findOrderByCustomer_Id(user.getCustomer().getId()).stream().map(order -> new OrderDTOOut(
                        order.getId(),
                        order.getStatus(),
                        order.getTotalPrice(),
                        order.getCreatedAt(),
                        order.getOrderItem().stream()
                                .map(item -> new OrderItemDTOOut(
                                        item.getProduct().getId(),
                                        item.getProduct().getName(),
                                        item.getQuantity(),
                                        item.getLinePrice())).toList())).toList();
    }



    public void createOrder(Integer userId, CreateOrderDTOIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Order order = new Order();
        order.setCustomer(user.getCustomer());
        order.setStatus("PENDING_PAYMENT");
        order.setCreatedAt(LocalDateTime.now());

        double totalPrice = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : dto.getItems().entrySet()) {

            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();

            if (quantity <= 0)
                throw new ApiException("Quantity must be greater than zero");

            Product product = productRepository.findProductById(productId);
            if (product == null)
                throw new ApiException("Product not found");

            if (!product.getIsActive())
                throw new ApiException("Product is not active");

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setLinePrice(product.getPrice().intValue() * quantity);
            item.setOrder(order);

            totalPrice += item.getLinePrice();
            orderItems.add(item);
        }

        order.setOrderItem(orderItems);
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
    }


    public String payOrder(Integer userId, Integer orderId, PaymentRequestDTOIn paymentRequestDTOIn) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (!order.getCustomer().getId().equals(user.getCustomer().getId()))
            throw new ApiException("This order does not belong to you");

        if (!order.getStatus().equals("PENDING_PAYMENT"))
            throw new ApiException("Order is not pending payment");

        if (order.getOrderItem() == null || order.getOrderItem().isEmpty())
            throw new ApiException("Order must contain items");


        for (OrderItem item : order.getOrderItem()) {
            Product product = productRepository.findProductById(item.getProduct().getId());

            if (product == null)
                throw new ApiException("Product not found");

            if (!product.getIsActive())
                throw new ApiException("Product is not active");

            Stock stock = product.getStock();
            if (stock == null)
                throw new ApiException("Stock not found");

            if (item.getQuantity() > stock.getTotalQuantity())
                throw new ApiException("Not enough stock for product: " + product.getName());
        }


        String paymentResponse = paymentService.startPayment(userId, orderId, paymentRequestDTOIn).getBody();

        order.setStatus("PAID");
        orderRepository.save(order);


        for (OrderItem item : order.getOrderItem()) {
            Stock stock = item.getProduct().getStock();
            stock.setTotalQuantity(stock.getTotalQuantity() - item.getQuantity());
            stockRepository.save(stock);
        }


        int total = order.getTotalPrice().intValue();
        int platformFee = Math.round(total * 0.05f);
        int sellerAmount = total - platformFee;


        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setStatus("PAID");
        invoice.setCurrency("SAR");
        invoice.setSubTotal(total);
        invoice.setTotal(total);
        invoice.setPlatformFee(platformFee);
        invoice.setSellerAmount(sellerAmount);

        invoiceRepository.save(invoice);


        StringBuilder itemsDetails = new StringBuilder();
        for (OrderItem item : order.getOrderItem()) {
            itemsDetails.append("• ")
                    .append(item.getProduct().getName())
                    .append(" | الكمية: ")
                    .append(item.getQuantity())
                    .append(" | السعر: ")
                    .append(item.getLinePrice())
                    .append(" ريال\n");
        }


        String emailBody =
                "مرحبًا " + user.getName() + " 🌿\n\n" +
                        "نشكر لك تسوقك من متجر غرس، يسعدنا إبلاغك بأن عملية الدفع تمت بنجاح.\n\n" +

                        "🧾 تفاصيل الطلب:\n" +
                        "----------------------------------\n" +
                        "رقم الطلب: " + order.getId() + "\n" +
                        "تاريخ الطلب: " + order.getCreatedAt() + "\n" +
                        "حالة الطلب: مدفوع\n\n" +

                        "🛒 المنتجات المطلوبة:\n" +
                        itemsDetails + "\n" +

                        "💰 ملخص المبلغ:\n" +
                        "إجمالي الطلب: " + total + " ريال\n" +


                        "📦 سيتم تجهيز طلبك وشحنه في أقرب وقت ممكن.\n\n" +
                        "إذا كان لديك أي استفسار، لا تتردد في التواصل معنا.\n\n" +
                        "مع خالص الشكر والتقدير،\n" +
                        "فريق متجر غرس 🌱";

        emailService.sendEmail(user.getEmail(), "فاتورة طلبك من متجر غرس 🌿 | رقم الطلب #" + order.getId(), emailBody
        );

        return paymentResponse;
    }





    public void updateOrderStatus(Integer userId, Integer orderId, String status) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Only admin can update order status");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (order.getDelivery() != null)
            throw new ApiException("Order status is managed by delivery");

        if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELED"))
            throw new ApiException("Order status cannot be updated");

        order.setStatus(status);
        orderRepository.save(order);
    }


    public void deleteOrder(Integer userId, Integer orderId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Only admin can delete order");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        orderRepository.delete(order);
    }


    public void requestReturn(Integer userId, Integer orderId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("CUSTOMER"))
//            throw new ApiException("Only customer can request return");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (!order.getCustomer().getId().equals(user.getCustomer().getId()))
            throw new ApiException("This order does not belong to you");

        if (!order.getStatus().equals("DELIVERED"))
            throw new ApiException("Only delivered orders can be returned");

        order.setStatus("RETURN_REQUESTED");
        orderRepository.save(order);
    }

    public void confirmReturn(Integer userId, Integer orderId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!(user.getRole().equals("ADMIN") || user.getRole().equals("FARMER")))
//            throw new ApiException("Access denied");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (!order.getStatus().equals("RETURN_REQUESTED"))
            throw new ApiException("Order is not requested for return");

        for (OrderItem item : order.getOrderItem()) {
            Stock stock = item.getProduct().getStock();
            stock.setTotalQuantity(stock.getTotalQuantity() + item.getQuantity());
            stockRepository.save(stock);
        }

        Payment payment = paymentRepository.findPaymentByOrder_Id(orderId);
        if (payment == null)
            throw new ApiException("Payment not found");

        payment.setStatus("REFUNDED");
        paymentRepository.save(payment);

        order.setStatus("REFUNDED");
        orderRepository.save(order);


        emailService.sendEmail(order.getCustomer().getUser().getEmail(), "Refund completed", "Your refund for order #" + order.getId() + " has been completed.");
    }

}
