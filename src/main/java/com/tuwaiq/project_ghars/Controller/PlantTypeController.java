package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.PlantType;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.AIService;
import com.tuwaiq.project_ghars.Service.PlantNetService;
import com.tuwaiq.project_ghars.Service.PlantTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/plant")
@RequiredArgsConstructor
public class PlantTypeController {

    private final PlantTypeService plantTypeService;
    private final PlantNetService plantNetService;
    private final AIService aiService;

    @PostMapping("/add")
    public ResponseEntity<?> addPlantType(@AuthenticationPrincipal User user, @RequestBody @Valid PlantType plantType) {
        plantTypeService.addPlantType(user.getId(), plantType);
        return ResponseEntity.status(200).body(new ApiResponse("Plant type added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllPlantTypes() {
        return ResponseEntity.status(200).body(plantTypeService.getAllPlantTypes());
    }

    @PutMapping("/update/{plantTypeId}")
    public ResponseEntity<?> updatePlantType(@AuthenticationPrincipal User user, @PathVariable Integer plantTypeId, @RequestBody @Valid PlantType plantType) {
        plantTypeService.updatePlantType(user.getId(), plantTypeId, plantType);
        return ResponseEntity.status(200).body(new ApiResponse("Plant type updated successfully"));
    }

    @DeleteMapping("/delete/{plantTypeId}")
    public ResponseEntity<?> deletePlantType(@AuthenticationPrincipal User user, @PathVariable Integer plantTypeId) {
        plantTypeService.deletePlantType(user.getId(), plantTypeId);
        return ResponseEntity.status(200).body(new ApiResponse("Plant type deleted successfully"));
    }

    @PostMapping("/identify/{organ}")
    public ResponseEntity<?> identifyPlant(@RequestBody MultipartFile image, @PathVariable String organ) throws IOException {
        String result = plantNetService.identifyPlant(image, organ);
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/identify-diseases/{organ}")
    public ResponseEntity<?> identifyPlantDiseases(@RequestBody MultipartFile image, @PathVariable String organ) throws IOException {
        String result = plantNetService.identifyPlantDiseases(image, organ);
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/learn/green-house")
    public ResponseEntity<?> LearnGreenHouseAI() {
        return ResponseEntity.status(200).body(aiService.greenHouseLearningAI());
    }

    @GetMapping("/learn/water-planting")
    public ResponseEntity<?> LearnWaterPlantingAI() {
        return ResponseEntity.status(200).body(aiService.waterPlantingLearningAI());
    }

}
