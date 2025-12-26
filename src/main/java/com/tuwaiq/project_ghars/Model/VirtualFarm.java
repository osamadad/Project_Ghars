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
    @Column(columnDefinition = "int primary key auto_increment")
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String field;

    @OneToMany(mappedBy = "virtualFarm", cascade = CascadeType.ALL)
    private Set<VirtualPlot> plots;

    @ManyToOne
    @JsonIgnore
    private Farmer farmer;
}
