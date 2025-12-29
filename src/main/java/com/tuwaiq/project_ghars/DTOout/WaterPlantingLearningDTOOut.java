package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WaterPlantingLearningDTOOut {

    private String definition;
    private List<String> benefits;
    private List<String> limitations;
    private List<String> waterPlantingTypes;
    private List<String> effectsOnPlants;
    private List<String> whenToUseWaterPlanting;
    private List<String> plantSuitability;
    private List<String> commonMisconceptions;
    private String aiSummary;
}
