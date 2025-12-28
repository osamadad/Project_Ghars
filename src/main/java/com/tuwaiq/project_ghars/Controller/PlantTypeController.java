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

    @GetMapping("/family/{family}")
    public ResponseEntity<?> getByFamily(@AuthenticationPrincipal User user, @PathVariable String family) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByFamily(user.getId(), family));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getByCategory(@AuthenticationPrincipal User user, @PathVariable String category) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByCategory(user.getId(), category));
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<?> getBySize(@AuthenticationPrincipal User user, @PathVariable String size) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesBySize(user.getId(), size));
    }

    @GetMapping("/growth-speed/{growthSpeed}")
    public ResponseEntity<?> getByGrowthSpeed(@AuthenticationPrincipal User user, @PathVariable String growthSpeed) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByGrowthSpeed(user.getId(), growthSpeed));
    }

    @GetMapping("/water-needs/{waterNeeds}")
    public ResponseEntity<?> getByWaterNeeds(@AuthenticationPrincipal User user, @PathVariable String waterNeeds) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByWaterNeeds(user.getId(), waterNeeds));
    }

    @GetMapping("/sun-needs/{sunNeeds}")
    public ResponseEntity<?> getBySunNeeds(@AuthenticationPrincipal User user, @PathVariable String sunNeeds) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesBySunNeeds(user.getId(), sunNeeds));
    }

    @GetMapping("/season/{season}")
    public ResponseEntity<?> getBySeason(@AuthenticationPrincipal User user, @PathVariable String season) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesBySeason(user.getId(), season));
    }

    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<?> getByDifficulty(@AuthenticationPrincipal User user, @PathVariable String difficultyLevel) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByDifficultyLevel(user.getId(), difficultyLevel));
    }

    @GetMapping("/growing-medium/{growingMedium}")
    public ResponseEntity<?> getByGrowingMedium(@AuthenticationPrincipal User user, @PathVariable String growingMedium) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByGrowingMedium(user.getId(), growingMedium));
    }

    @GetMapping("/planting-place/{plantingPlace}")
    public ResponseEntity<?> getByPlantingPlace(@AuthenticationPrincipal User user, @PathVariable String plantingPlace) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByPlantingPlace(user.getId(), plantingPlace));
    }

    @GetMapping("/life-span/{lifeSpan}")
    public ResponseEntity<?> getByLifeSpan(@AuthenticationPrincipal User user, @PathVariable String lifeSpan) {
        return ResponseEntity.status(200).body(plantTypeService.getPlantTypesByLifeSpan(user.getId(), lifeSpan));
    }
}
