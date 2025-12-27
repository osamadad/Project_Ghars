package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GreenHouseLearningDTOOut {

    private String definition;
    private List<String> benefits;
    private List<String> limitations;
    private List<String> greenhouseTypes;
    private List<String> effectsOnPlants;
    private List<String> whenToUseAGreenhouse;
    private List<String> basicComponents;
    private List<String> commonMisconceptions;
    private String aiSummary;
}
