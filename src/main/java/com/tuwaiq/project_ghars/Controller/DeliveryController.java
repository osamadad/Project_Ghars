package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/my/{userId}")
    public ResponseEntity<?> getMyDeliveries(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(deliveryService.getMyDeliveries(userId));
    }

    @PostMapping("/create/{userId}/{orderId}/{driverId}")
    public ResponseEntity<?> createDelivery(@PathVariable Integer userId, @PathVariable Integer orderId, @PathVariable Integer driverId) {
        deliveryService.createDelivery(userId, orderId, driverId);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery created successfully"));
    }

    @PutMapping("/update-status/{userId}/{deliveryId}")
    public ResponseEntity<?> updateDeliveryStatus(@PathVariable Integer userId, @PathVariable Integer deliveryId, @RequestParam String status) {
        deliveryService.updateDeliveryStatus(userId, deliveryId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery status updated successfully"));
    }
}
