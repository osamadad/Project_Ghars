package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class CreateOrderDTOIn {

    @NotEmpty(message = "Order items are required")
    private Map<
            @NotNull(message = "Product id is required")
                    Integer,
            @NotNull(message = "Quantity is required")
                    Integer
            > items;
}
