package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.CreateOrderDTOIn;
import com.tuwaiq.project_ghars.DTOIn.PaymentRequestDTOIn;
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
        return ResponseEntity.ok(orderService.getAllOrders(user.getId()));
    }


    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getMyOrders(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal User user, @Valid @RequestBody CreateOrderDTOIn dto
    ) {orderService.createOrder(user.getId(), dto);
        return ResponseEntity.ok(new ApiResponse("Order created successfully"));
    }




    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> payOrder(@AuthenticationPrincipal User user, @PathVariable Integer orderId, @Valid @RequestBody PaymentRequestDTOIn paymentRequestDTOIn
    ) {
        String response = orderService.payOrder(user.getId(), orderId, paymentRequestDTOIn);
        return ResponseEntity.status(200).body(response);
    }


    @PostMapping("/return/{orderId}")
    public ResponseEntity<?> requestReturn(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.requestReturn(user.getId(), orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Return request submitted successfully"));
    }


    @PostMapping("/confirm-return/{orderId}")
    public ResponseEntity<?> confirmReturn(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.confirmReturn(user.getId(), orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Return confirmed and refund completed"));
    }


    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<?> updateOrderStatus(@AuthenticationPrincipal User user, @PathVariable Integer orderId, @RequestParam String status) {
        orderService.updateOrderStatus(user.getId(), orderId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Order status updated successfully"));}


    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<?> deleteOrder(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.deleteOrder(user.getId(), orderId);
        return ResponseEntity.status(200).body(new ApiResponse("Order deleted successfully"));
    }
}

