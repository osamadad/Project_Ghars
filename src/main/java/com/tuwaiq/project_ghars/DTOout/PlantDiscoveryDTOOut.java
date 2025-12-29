package com.tuwaiq.project_ghars.DTOout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlantDiscoveryDTOOut {

    private String overview;
    private String whyGrowThisPlant;
    private String timeToGrow;
    private String plantNeeds;
    private String growingExperience;
    private String commonChallenges;
    private String whoThisPlantIsGoodFor;
    private List<String> funFacts;
}
