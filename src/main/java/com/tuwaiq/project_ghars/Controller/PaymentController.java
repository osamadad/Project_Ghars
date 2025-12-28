package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.DTOIn.PaymentRequestDTOIn;
import com.tuwaiq.project_ghars.Service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/start")
    public ResponseEntity<?> startPayment(
            @AuthenticationPrincipal com.tuwaiq.project_ghars.Model.User user, @Valid @RequestBody PaymentRequestDTOIn dto) {
        return ResponseEntity.status(200).body(paymentService.startPayment(user.getId(), dto.getOrderId()));
    }



}
