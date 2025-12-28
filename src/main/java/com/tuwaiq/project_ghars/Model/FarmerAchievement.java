package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
public class FarmerAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column()
    private LocalDateTime unlockedAt;

    @ManyToOne
    private Achievement achievement;

    @ManyToOne
    @JsonIgnore
    private Farmer farmer;
}
