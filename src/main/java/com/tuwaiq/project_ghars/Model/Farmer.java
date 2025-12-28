package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Farmer {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(50)")
    private String farmerRank;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer farmerExperience;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer totalYield;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer seasonalYield;

    @ManyToOne
    private Level level;

    @ManyToOne
    private FarmerAchievement farmerAchievement;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Farm> farms;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<VirtualFarm> virtualFarms;
}
