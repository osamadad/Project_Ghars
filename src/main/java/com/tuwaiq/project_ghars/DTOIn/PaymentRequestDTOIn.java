package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTOIn {

    @NotNull(message = "Order id is required")
    private Integer orderId;
}
