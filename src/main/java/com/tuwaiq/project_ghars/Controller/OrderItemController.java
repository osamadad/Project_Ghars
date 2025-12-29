package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.OrderItem;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-item")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrderItems(@AuthenticationPrincipal User user,
                                           @PathVariable Integer orderId) {
        return ResponseEntity.status(200).body(orderItemService.getOrderItems(user.getId(), orderId));
    }

    @PostMapping("/add/{orderId}")
    public ResponseEntity<?> addOrderItem(@AuthenticationPrincipal User user, @PathVariable Integer orderId, @Valid @RequestBody OrderItem orderItem) {
        orderItemService.addOrderItem(user.getId(), orderId, orderItem);
        return ResponseEntity.status(200).body(new ApiResponse("Order item added successfully"));
    }

    @PutMapping("/update/{orderItemId}")
    public ResponseEntity<?> updateOrderItem(@AuthenticationPrincipal User user, @PathVariable Integer orderItemId, @Valid @RequestBody OrderItem orderItem) {
        orderItemService.updateOrderItem(user.getId(), orderItemId, orderItem);
        return ResponseEntity.status(200).body(new ApiResponse("Order item updated successfully"));
    }

    @DeleteMapping("/delete/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem(@AuthenticationPrincipal User user, @PathVariable Integer orderItemId) {
        orderItemService.deleteOrderItem(user.getId(), orderItemId);
        return ResponseEntity.status(200).body(new ApiResponse("Order item deleted successfully"));
    }
}



