package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Customer;
import com.tuwaiq.project_ghars.Model.Order;
import com.tuwaiq.project_ghars.Model.OrderItem;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.CustomerRepository;
import com.tuwaiq.project_ghars.Repository.OrderRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

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

        if (order.getTotalPrice() <= 0)
            throw new ApiException("Total price must be greater than zero");

        order.setStatus("PENDING_PAYMENT");
        order.setCreatedAt(LocalDateTime.now());
        order.setCustomer(user.getCustomer());

        for (OrderItem item : order.getOrderItem()) {
            item.setOrder(order);
        }

        orderRepository.save(order);
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

        if (order.getStatus().equals("DELIVERED") ||
                order.getStatus().equals("CANCELED"))
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
}
