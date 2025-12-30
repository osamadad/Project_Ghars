package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderDTOOut {

    private Integer id;
    private String status;
    private Double totalPrice;
    private LocalDateTime createdAt;
    private List<OrderItemDTOOut> items;
}
