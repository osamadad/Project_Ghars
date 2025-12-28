package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Order;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200)
                .body(orderService.getAllOrders(user.getId()));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getMyOrders(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal User user, @Valid @RequestBody Order order) {
        orderService.createOrder(user.getId(), order);
        return ResponseEntity.status(200).body(new ApiResponse("Order created successfully"));
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@AuthenticationPrincipal User user, @PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(user.getId(), orderId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Order status updated successfully"));
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.deleteOrder(user.getId(), orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Order deleted successfully"));
    }
}
