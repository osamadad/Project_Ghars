package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Pattern;
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
public class PlantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Sorry, the plant name can't be empty, please try again")
    @Column()
    private String name;
    @NotEmpty(message = "Sorry, the plant family can't be empty, please try again")
    @Column()
    private String family;
    @NotEmpty(message = "Sorry, the plant season can't be empty, please try again")
    @Column()
    private String season;
    @NotEmpty(message = "Sorry, the plant category can't be empty, please try again")
    @Pattern(regexp = "Vegetable| Fruit | Flower | Herb", message = "Sorry, the plant category must be Vegetable, Fruit, Flower, or Herb, please try again")
    @Column()
    private String category;
    @NotEmpty(message = "Sorry, the plant growth time can't be empty, please try again")
    @Column()
    private Integer growthTime;
    @NotEmpty(message = "Sorry, the plant type can't be empty, please try again")
    @Pattern(regexp = "Seed | Seedling | Grown", message = "Sorry, the farm size must be Seed, Seedling, or Grown, please try again")
    @Column()
    private String type;
    @NotEmpty(message = "Sorry, the plant unit can't be empty, please try again")
    @Pattern(regexp = "Kg | Pack", message = "Sorry, the farm size must be Kg, or Pack, please try again")
    @Column()
    private String unit;
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, mappedBy = "plantType")
    private Set<Field> fields;
    @OneToMany
    private Set<VirtualPlot> virtualPlots;
}
