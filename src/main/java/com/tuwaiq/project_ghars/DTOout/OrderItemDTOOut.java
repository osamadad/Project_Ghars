package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTOOut {

    private Integer productId;
    private String productName;
    private Integer quantity;
    private Integer linePrice;
}
