package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddProductDTOIn {

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotEmpty
    @Pattern(regexp = "HARVESTED_PRODUCT|PLANT_SEEDLING|SEEDS")
    private String sellType;

    @NotNull
    private Boolean isActive;


    private String photoUrl;


    @NotNull
    @PositiveOrZero
    private Integer totalQuantity;

    @NotEmpty
    @Pattern(regexp = "PIECE|PACK|BUNCH")
    private String unit;

    @NotNull
    private Integer farmId;
}
