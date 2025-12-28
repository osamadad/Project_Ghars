package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.DTOIn.PaymentRequestDTOIn;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay/{orderId}")
    public ResponseEntity<?> startPayment(
            @AuthenticationPrincipal User user, @PathVariable Integer orderId, @RequestBody PaymentRequestDTOIn paymentRequestDTOIn) {
        return ResponseEntity.status(200).body(paymentService.startPayment(user.getId(), orderId, paymentRequestDTOIn));
    }



}
