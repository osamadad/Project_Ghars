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
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(50) not null")
    private String title;

    @NotEmpty
    @Column(columnDefinition = "varchar(255) not null")
    private String task;

    @Column(columnDefinition = "boolean default false")
    private Boolean isCompleted = false;

    @ManyToOne
    @JsonIgnore
    private Farmer farmer;
}
