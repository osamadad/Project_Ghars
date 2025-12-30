package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class InvoiceDTOout {

    private Integer invoiceId;
    private String status;
    private String currency;
    private Integer subTotal;
    private Integer total;

    private Integer orderId;
    private String orderStatus;
    private LocalDateTime orderDate;
}
