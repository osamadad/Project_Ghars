package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VirtualPlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String name;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null")
    private String status;

    @Column(columnDefinition = "double")
    private Double sunMeter;

    @Column(columnDefinition = "double")
    private Double waterMeter;

    @ManyToOne
    @JsonIgnore
    private VirtualFarm virtualFarm;
}
