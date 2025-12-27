package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NotEmpty(message = "Sorry, the plant common name can't be empty, please try again")
    @Column()
    private String commonName;
    @NotEmpty(message = "Sorry, the plant scientific name can't be empty, please try again")
    @Column(columnDefinition = "varchar(255) unique")
    private String scientificName;
    @NotEmpty(message = "Sorry, the plant family can't be empty, please try again")
    @Column()
    private String family;
    @NotEmpty(message = "Sorry, the plant category can't be empty, please try again")
    @Pattern(regexp = "fruit|vegetable|flower|herb", message = "Sorry, the plant category must be fruit, vegetable, flower, or herb, please try again")
    @Column()
    private String category;
    @NotEmpty(message = "Sorry, the plant life span can't be empty, please try again")
    @Pattern(regexp = "annual|perennial|biennial", message = "Sorry, the plant life span must be annual, perennial, or biennial, please try again")
    @Column()
    private String lifeSpan;
    @NotEmpty(message = "Sorry, the plant native region can't be empty, please try again")
    @Column()
    private String nativeRegion;
    @NotEmpty(message = "Sorry, the plant growth speed can't be empty, please try again")
    @Pattern(regexp = "slow|normal|fast", message = "Sorry, the plant growth speed must be slow, normal, or fast, please try again")
    @Column()
    private String growthSpeed;
    @NotEmpty(message = "Sorry, the expected time to grow can't be empty, please try again")
    @Pattern(regexp = "^[0-9]+\\s(days|months|years)$", message = "Sorry, the expected time to grow must be in this format: number + days, months, or years, please try again")
    @Column()
    private String expectedTimeToGrow;
    @NotEmpty(message = "Sorry, the plant size can't be empty, please try again")
    @Pattern(regexp = "small|medium|large", message = "Sorry, the plant size must be small, medium, or large, please try again")
    @Column()
    private String size;
    @NotEmpty(message = "Sorry, the plant water needs can't be empty, please try again")
    @Pattern(regexp = "low|medium|high", message = "Sorry, the plant water needs must be low, medium, or high, please try again")
    @Column()
    private String waterNeeds;
    @NotEmpty(message = "Sorry, the plant sun needs can't be empty, please try again")
    @Pattern(regexp = "low|medium|high", message = "Sorry, the plant sun needs must be low, medium, or high, please try again")
    @Column()
    private String sunNeeds;
    @NotEmpty(message = "Sorry, the plant temperature needs can't be empty, please try again")
    @Pattern(regexp = "cold|medium|hot", message = "Sorry, the plant temperature needs must be cold, medium, or hot, please try again")
    @Column()
    private String temperatureNeeds;
    @NotEmpty(message = "Sorry, the growing medium can't be empty, please try again")
    @Pattern(regexp = "soil|water|both", message = "Sorry, the growing medium must be soil, water, or both, please try again")
    @Column()
    private String growingMedium;
    @NotEmpty(message = "Sorry, the planting place can't be empty, please try again")
    @Pattern(regexp = "indoor|outdoor|both", message = "Sorry, the planting place must be indoor, outdoor, or both, please try again")
    @Column()
    private String plantingPlace;
    @NotEmpty(message = "Sorry, the plant season can't be empty, please try again")
    @Pattern(regexp = "winter|spring|summer|autumn", message = "Sorry, the plant season must be winter, spring, summer, or autumn, please try again")
    @Column()
    private String season;
    @NotEmpty(message = "Sorry, the difficulty level can't be empty, please try again")
    @Pattern(regexp = "easy|medium|hard", message = "Sorry, the difficulty level must be easy, medium, or hard, please try again")
    @Column()
    private String difficultyLevel;
    @NotEmpty(message = "Sorry, the common risks can't be empty, please try again")
    @Pattern(regexp = "^(overwatering|pests|disease|temperature_stress)(,(overwatering|pests|disease|temperature_stress))*$", message = "Sorry, the common risks must be one or more of: overwatering, pests, disease, or temperature stress, please try again")
    @Column()
    private String commonRisks;
    @NotEmpty(message = "Sorry, the plant type can't be empty, please try again")
    @Pattern(regexp = "Seed | Seedling | Grown", message = "Sorry, the farm size must be Seed, Seedling, or Grown, please try again")
    @Column()
    private String type;
    @NotEmpty(message = "Sorry, the plant unit can't be empty, please try again")
    @Pattern(regexp = "PIECE|PACK|BUNCH", message = "Sorry, the farm size must be PIECE, PACK, or BUNCH, please try again")
    @Column()
    private String unit;
    @ManyToMany(mappedBy = "plantTypes")
    @JsonIgnore
    private Set<Field> fields;
    @OneToMany
    @JsonIgnore
    private Set<VirtualPlot> virtualPlots;
}
