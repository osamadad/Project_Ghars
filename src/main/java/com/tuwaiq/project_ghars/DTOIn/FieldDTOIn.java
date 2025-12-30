package com.tuwaiq.project_ghars.DTOIn;

import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.PlantType;
import com.tuwaiq.project_ghars.Model.Yield;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
public class FieldDTOIn {

    @NotNull(message = "Sorry, the field expected yield can't be empty, please try again")
    @Positive(message = "Sorry, the field expected yield must be positive, please try again")
    private Double expectedYield;

    @NotNull(message = "Sorry, the field expected yield time can't be empty, please try again")
    @Future(message = "Sorry, the field expected yield time must be in the future, please try again")
    private LocalDateTime expectedYieldTime;

    private Integer farmId;

    private Integer plantTypeId;
}
