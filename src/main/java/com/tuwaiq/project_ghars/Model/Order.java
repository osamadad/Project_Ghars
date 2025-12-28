package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Order status is required")
    @Pattern(regexp = "PENDING_PAYMENT|PAID|CONFIRMED|PACKED|SHIPPED|DELIVERED|CANCELED|REFUNDED",
            message = "Order status must be PENDING_PAYMENT, PAID, CONFIRMED, PACKED, SHIPPED, DELIVERED, CANCELED, or REFUNDED")
    private String status;


    @NotNull(message = "Total price is required")
    @Positive(message = "Total price must be greater than zero")
    private Double totalPrice;

    @NotNull(message = "Order creation date is required")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItem;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;

}
