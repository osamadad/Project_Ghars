package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Total quantity is required")
    @PositiveOrZero(message = "Total quantity cannot be negative")
    private Integer totalQuantity;

    @NotEmpty(message = "Unit is required")
    @Pattern(regexp = "PIECE|PACK|BUNCH", message = "Unit must be PIECE, PACK, or BUNCH"
    )
    private String unit;

    @NotNull(message = "Last update time is required")
    private LocalDateTime lastUpdate;


    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;


    @ManyToOne
    private Yield yield;
}

