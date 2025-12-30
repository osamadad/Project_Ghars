package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.OrderItemDTOIn;
import com.tuwaiq.project_ghars.Model.Order;
import com.tuwaiq.project_ghars.Model.OrderItem;
import com.tuwaiq.project_ghars.Model.Product;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.OrderItemRepository;
import com.tuwaiq.project_ghars.Repository.OrderRepository;
import com.tuwaiq.project_ghars.Repository.ProductRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public List<OrderItem> getOrderItems(Integer userId, Integer orderId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("ADMIN"))
//            throw new ApiException("Access denied");

        return orderItemRepository.findOrderItemByOrder_Id(orderId);
    }

    public void addOrderItem(Integer userId, Integer orderId, OrderItemDTOIn dto) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

        Order order = orderRepository.findOrderById(orderId);
        if (order == null)
            throw new ApiException("Order not found");

        if (!order.getCustomer().getId().equals(user.getCustomer().getId()))
            throw new ApiException("This order does not belong to you");

        if (!order.getStatus().equals("PENDING_PAYMENT"))
            throw new ApiException("Cannot modify order items at this stage");

        Product product = productRepository.findProductById(dto.getProductId());
        if (product == null)
            throw new ApiException("Product not found");

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setLinePrice(product.getPrice().intValue() * dto.getQuantity());
        item.setOrder(order);

        orderItemRepository.save(item);
    }


    public void updateOrderItem(Integer userId, Integer orderItemId, OrderItem orderItem) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("CUSTOMER"))
//            throw new ApiException("Only customer can update order items");

        OrderItem oldItem = orderItemRepository.findOrderItemById(orderItemId);
        if (oldItem == null)
            throw new ApiException("Order item not found");

        Order order = oldItem.getOrder();

        if (!order.getCustomer().getId().equals(user.getCustomer().getId()))
            throw new ApiException("This order does not belong to you");

        if (!order.getStatus().equals("PENDING_PAYMENT"))
            throw new ApiException("Cannot modify order items at this stage");

        if (orderItem.getQuantity() <= 0)
            throw new ApiException("Quantity must be greater than zero");

        oldItem.setQuantity(orderItem.getQuantity());
        oldItem.setLinePrice(
                oldItem.getProduct().getPrice().intValue() * orderItem.getQuantity()
        );

        orderItemRepository.save(oldItem);
    }

    public void deleteOrderItem(Integer userId, Integer orderItemId) {

        User user = userRepository.findUserById(userId);
        if (user == null)
            throw new ApiException("User not found");

//        if (!user.getRole().equals("CUSTOMER"))
//            throw new ApiException("Only customer can delete order items");

        OrderItem orderItem = orderItemRepository.findOrderItemById(orderItemId);
        if (orderItem == null)
            throw new ApiException("Order item not found");

        Order order = orderItem.getOrder();

        if (!order.getCustomer().getId().equals(user.getCustomer().getId()))
            throw new ApiException("This order does not belong to you");

        if (!order.getStatus().equals("PENDING_PAYMENT"))
            throw new ApiException("Cannot modify order items at this stage");

        orderItemRepository.delete(orderItem);
    }



}
