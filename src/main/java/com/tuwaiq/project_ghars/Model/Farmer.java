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
public class Farmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String experience;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String level;

    @OneToOne
    @MapsId
    @JsonIgnore
    private User user;


    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    private Set<Farm> farms;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    private Set<VirtualFarm> virtualFarms;

    @OneToMany(mappedBy = "farmer", cascade = CascadeType.ALL)
    private Set<Achievement> achievements;
}
