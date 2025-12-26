package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Pattern(regexp = " Seedling | Growing | Ready | Harvested") /* reference only */
    @Column()
    private String status;
    @Column()
    private Double expectedYield;
    @Column()
    private LocalDateTime expectedYieldTime;
    @Column()
    private LocalDateTime createdAt;
    @ManyToOne
    private Farm farm;
    @ManyToMany
    private Set<PlantType> plantTypes;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "field")
    private Set<Yield> yields;
}
