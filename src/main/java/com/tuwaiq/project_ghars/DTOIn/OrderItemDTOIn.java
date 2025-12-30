package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemDTOIn {

    @NotNull(message = "Product id is required")
    private Integer productId;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be at least 1")
    private Integer quantity;
}
