package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Product name is required")
    private String name;

    @NotEmpty(message = "Product description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotEmpty(message = "Sell type is required")
    @Pattern(regexp = "HARVESTED_PRODUCT|PLANT_SEEDLING|SEEDS", message = "Sell type must be HARVESTED_PRODUCT or PLANT_SEEDLING or SEEDS")
    private String sellType;

    @NotNull(message = "Product active status is required")
    private Boolean isActive;

    @NotEmpty(message = "Photo URL is required")
    private String photoUrl;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;
}
