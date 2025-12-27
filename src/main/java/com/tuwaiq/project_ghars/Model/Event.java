package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(100) not null")
    private String title;

    @NotEmpty
    @Column(columnDefinition = "varchar(500) not null")
    private String description;

    @NotEmpty
    @Column(columnDefinition = "varchar(100) not null")
    private String location;

    @NotNull
    @Column(columnDefinition = "date not null")
    private LocalDate date;

    @NotNull
    @Column(columnDefinition = "time not null")
    private LocalTime startTime;

    @NotNull
    @Column(columnDefinition = "time not null")
    private LocalTime endTime;

    @ManyToMany
    @JsonIgnore
    private Set<User> users ;
}
