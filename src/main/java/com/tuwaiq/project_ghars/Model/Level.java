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
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer levelNumber;

    @NotNull
    @Column(columnDefinition = "int not null")
    private Integer requiredExp;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Farmer> farmers;
}
