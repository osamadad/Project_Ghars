package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Yield {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column()
    private String harvestDate;
    @Column()
    private Double harvestYield;
    @Column()
    private String quality;
    @ManyToOne
    private Field field;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "yield")
    @JsonIgnore
    private Set<Stock> stock;
}
