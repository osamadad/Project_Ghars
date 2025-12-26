package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.PlantType;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.PlantTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
}
