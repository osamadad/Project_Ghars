package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id ;

    @NotEmpty
    @Column()
    private String title ;

    @NotEmpty
    @Column()
    private String description ;

    @NotNull
    @Column()
    private Integer rating ;

    @Column()
    private LocalDateTime createdAt ;

    @ManyToOne
    private Farm farm ;
}
