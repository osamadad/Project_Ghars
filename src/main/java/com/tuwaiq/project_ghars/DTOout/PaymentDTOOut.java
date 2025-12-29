package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDTOOut {

    private Integer paymentId;
    private String status;
    private String provider;
    private Integer amount;
    private String moyasarPaymentId;
}
