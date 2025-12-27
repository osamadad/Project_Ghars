package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Delivery status is required")
    private String status;

    @NotNull(message = "Estimated delivery is required")
    private String estimatedDelivery;


    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @OneToOne
    @JoinColumn(name = "order_id", unique = true)
    private Order order;

}
