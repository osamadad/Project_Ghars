package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/my")
    public ResponseEntity<?> getMyDeliveries(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(deliveryService.getMyDeliveries(user.getId()));
    }

    @PostMapping("/create/{orderId}/{driverId}")
    public ResponseEntity<?> createDelivery(@AuthenticationPrincipal User user, @PathVariable Integer orderId, @PathVariable Integer driverId) {
        deliveryService.createDelivery(user.getId(), orderId, driverId);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery created successfully"));
    }

    @PutMapping("/update-status/{deliveryId}")
    public ResponseEntity<?> updateDeliveryStatus(@AuthenticationPrincipal User user, @PathVariable Integer deliveryId, @RequestParam String status) {
        deliveryService.updateDeliveryStatus(user.getId(), deliveryId, status);
        return ResponseEntity.status(200).body(new ApiResponse("Delivery status updated successfully"));
    }
}
