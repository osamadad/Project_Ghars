package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Order;
import com.tuwaiq.project_ghars.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get-all/{userId}")
    public ResponseEntity<?> getAllOrders(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(orderService.getAllOrders(userId));
    }

    // CUSTOMER
    @GetMapping("/my-orders/{userId}")
    public ResponseEntity<?> getMyOrders(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(orderService.getMyOrders(userId));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId, @Valid @RequestBody Order order) {
        orderService.createOrder(userId, order);
        return ResponseEntity.status(200).body(new ApiResponse("Order created successfully"));
    }

    @PutMapping("/update-status/{userId}/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Integer userId, @PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(userId, orderId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Order status updated successfully"));
    }

    @DeleteMapping("/delete/{userId}/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer userId, @PathVariable Integer orderId) {
        orderService.deleteOrder(userId, orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Order deleted successfully"));
    }
}
