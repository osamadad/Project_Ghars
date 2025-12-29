package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.PaymentRequestDTOIn;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

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


    public List<Order> getAllOrders(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Access denied");

        return orderRepository.findAll();
    }

    public List<Order> getMyOrders(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("CUSTOMER"))
            throw new ApiException("Only customer can access this");

        return orderRepository.findOrderByCustomer_Id(
                user.getCustomer().getId()
        );
    }


    public void createOrder(Integer userId, Order order) {
        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("CUSTOMER"))
            throw new ApiException("Only customer can create order");

        if (order.getOrderItem() == null || order.getOrderItem().isEmpty())
            throw new ApiException("Order must contain items");
        double totalPrice = 0;

        for (OrderItem item : order.getOrderItem()) {
            Product product = productRepository.findProductById(item.getProduct().getId());

            if (product == null)
                throw new ApiException("Product not found");

            if (!product.getIsActive())
                throw new ApiException("Product is not active");

            item.setOrder(order);
            item.setLinePrice(product.getPrice().intValue() * item.getQuantity()
            );

            totalPrice += item.getLinePrice();
        }

        order.setTotalPrice(totalPrice);
        order.setStatus("PENDING_PAYMENT");
        order.setCreatedAt(LocalDateTime.now());
        order.setCustomer(user.getCustomer());

        orderRepository.save(order);
    }



    public String payOrder(Integer userId, Integer orderId, PaymentRequestDTOIn paymentRequestDTOIn) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("CUSTOMER"))
            throw new ApiException("Only customer can pay order");

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
                throw new ApiException(
                        "Not enough stock for product: " + product.getName()
                );
        }

        String paymentResponse = paymentService.startPayment(userId, orderId, paymentRequestDTOIn).getBody();

        order.setStatus("PAID");
        orderRepository.save(order);

        for (OrderItem item : order.getOrderItem()) {
            Stock stock = item.getProduct().getStock();
            stock.setTotalQuantity(stock.getTotalQuantity() - item.getQuantity());
            stockRepository.save(stock);
        }

        Invoice invoice = new Invoice();
        invoice.setOrder(order);
        invoice.setStatus("PAID");
        invoice.setCurrency(1);
        invoice.setSubTotal(order.getTotalPrice().intValue());
        invoice.setTotal(order.getTotalPrice().intValue());
        invoiceRepository.save(invoice);

        emailService.sendEmail(user.getEmail(), "Invoice for order #" + order.getId(), "Your order has been paid successfully."
        );

        return paymentResponse;
    }


    public void updateOrderStatus(Integer userId, Integer orderId, String status) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Only admin can update order status");

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

        if (!user.getRole().equals("ADMIN"))
            throw new ApiException("Only admin can delete order");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        orderRepository.delete(order);
    }


    public void requestReturn(Integer userId, Integer orderId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        if (!user.getRole().equals("CUSTOMER"))
            throw new ApiException("Only customer can request return");

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

        if (!(user.getRole().equals("ADMIN") || user.getRole().equals("FARMER")))
            throw new ApiException("Access denied");

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
