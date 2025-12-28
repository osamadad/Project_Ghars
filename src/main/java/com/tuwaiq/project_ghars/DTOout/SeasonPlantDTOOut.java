package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SeasonPlantDTOOut {
    private String commonName;
    private String category;
    private String difficultyLevel;
    private String aiReason;
}
