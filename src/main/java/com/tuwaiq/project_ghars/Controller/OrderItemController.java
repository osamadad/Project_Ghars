package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.OrderItem;
import com.tuwaiq.project_ghars.Service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-item")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/get/{userId}/{orderId}")
    public ResponseEntity<?> getOrderItems(@PathVariable Integer userId, @PathVariable Integer orderId) {
        return ResponseEntity.status(200)
                .body(orderItemService.getOrderItems(userId, orderId));
    }

    @PostMapping("/add/{userId}/{orderId}")
    public ResponseEntity<?> addOrderItem(@PathVariable Integer userId, @PathVariable Integer orderId, @Valid @RequestBody OrderItem orderItem) {
        orderItemService.addOrderItem(userId, orderId, orderItem);
        return ResponseEntity.status(200).body(new ApiResponse("Order item added successfully"));
    }

    @PutMapping("/update/{userId}/{orderItemId}")
    public ResponseEntity<?> updateOrderItem(@PathVariable Integer userId, @PathVariable Integer orderItemId, @Valid @RequestBody OrderItem orderItem) {
        orderItemService.updateOrderItem(userId, orderItemId, orderItem);
        return ResponseEntity.status(200).body(new ApiResponse("Order item updated successfully"));
    }

    @DeleteMapping("/delete/{userId}/{orderItemId}")
    public ResponseEntity<?> deleteOrderItem(@PathVariable Integer userId, @PathVariable Integer orderItemId) {
        orderItemService.deleteOrderItem(userId, orderItemId);
        return ResponseEntity.status(200).body(new ApiResponse("Order item deleted successfully"));
    }


}
