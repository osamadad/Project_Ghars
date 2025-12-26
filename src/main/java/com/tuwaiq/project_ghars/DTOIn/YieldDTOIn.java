package com.tuwaiq.project_ghars.DTOIn;

import com.tuwaiq.project_ghars.Model.Field;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YieldDTOIn {

    @NotEmpty(message = "Sorry, the harvest date can't be empty, please try again")
    @FutureOrPresent(message = "Sorry, the harvest date can't be in the past, please try again")
    private String harvestDate;
    @Positive(message = "Sorry, the harvest yield must be positive, please try again")
    private Double harvestYield;
    @NotEmpty(message = "Sorry, the yield quality can't be empty, please try again")
    private String quality;
    private Integer fieldId;
}
