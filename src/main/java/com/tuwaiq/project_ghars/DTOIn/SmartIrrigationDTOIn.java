package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SmartIrrigationDTOIn {

    @NotEmpty
    private String plant;

    @NotEmpty
    private String season;

    private String location;
}
