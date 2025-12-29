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
public class VirtualFarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Sorry, the virtual farm can't be empty, please try again")
    private String name;

    private Integer maxPlot;

    @OneToMany(mappedBy = "virtualFarm", cascade = CascadeType.ALL)
    private Set<VirtualPlot> plots;

    @ManyToOne
    @JsonIgnore
    private Farmer farmer;
}
