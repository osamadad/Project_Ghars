package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VirtualPlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Sorry, the plot type can't be empty, please try again")
    @Column()
    private String plotType;

    @Column()
    private Integer progress;

    @Column()
    private Integer health;

    @Column()
    private String status;

    @Column()
    private Integer expectedYield;

    @Column()
    private Integer actualYield;

    @Column()
    private Integer experienceGiven;

    @Column()
    private Integer knowledgeMeter;

    @Column()
    private Integer waterMeter;

    @Column()
    private Integer sunMeter;

    @Column()
    private String verificationPic;

    @Column()
    private LocalDateTime plantedAt;

    @ManyToOne
    @JsonIgnore
    private VirtualFarm virtualFarm;

    @ManyToOne
    private PlantType plantType;
}
