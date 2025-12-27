package com.tuwaiq.project_ghars.DTOIn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuwaiq.project_ghars.Model.Field;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class FarmDTOIn {

    private String license;
    @NotEmpty(message = "Sorry, the farm name can't be empty, please try again")
    private String name;
    @NotEmpty(message = "Sorry, the farm description can't be empty, please try again")
    private String description;
    @NotEmpty(message = "Sorry, the farm size can't be empty, please try again")
    @Pattern(regexp = "Small| Medium | Large", message = "Sorry, the farm size must be Small, Medium, or large, please try again")
    private String size;
    private String speciality;
    private String photoUrl;
    private Integer farmerId;

}
