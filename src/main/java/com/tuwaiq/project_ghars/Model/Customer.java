package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(255) not null")
    private String favorites;


    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    private Set<Order> order;
}
