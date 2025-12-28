package com.tuwaiq.project_ghars.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "varchar(255) unique")
    private String license;
    @Column()
    private String licenseStaus = null;
    @Column()
    private String name;
    @Column()
    private String description;
    @Column()
    private String size;
    @Column()
    private String speciality;
    @Column()
    private Double rating;
    @Column()
    private String photoUrl;
    @Column()
    private LocalDateTime createdAt;
    @ManyToOne
    @MapsId
    @JsonIgnore
    private Farmer farmer;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "farm")
    @JsonIgnore
    private Set<Field> fields;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "farm")
    @JsonIgnore
    private Set<Review> reviews;

}
