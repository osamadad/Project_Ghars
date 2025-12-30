package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Invoice status is required")
    private String status;

    @NotEmpty(message = "Currency is required")
    private String currency;

    @NotNull(message = "Subtotal is required")
    @Positive(message = "Subtotal must be greater than zero")
    private Integer subTotal;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total must be greater than zero")
    private Integer total;


    private Integer platformFee;


    private Integer sellerAmount;


    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
