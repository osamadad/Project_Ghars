package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.FarmerDTOIn;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farmer")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerFarmer(@RequestBody @Valid FarmerDTOIn farmerDTOIn) {
        farmerService.registerFarmer(farmerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Farmer registered"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFarmer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(farmerService.getAllFarmer());
    }

    @GetMapping("/get-my-info")
    public ResponseEntity<?> getMyFarmer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(farmerService.getMyFarmer(user.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMyFarmer(@AuthenticationPrincipal User user, @RequestBody @Valid FarmerDTOIn farmerDTOIn) {
        farmerService.updateMyFarmer(user.getId(), farmerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Farmer updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyFarmer(@AuthenticationPrincipal User user) {
        farmerService.deleteMyFarmer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Farmer deleted"));
    }

    @GetMapping("/by-city/{city}")
    public ResponseEntity<?> getFarmersByCity(@AuthenticationPrincipal User user, @PathVariable String city) {
        return ResponseEntity.status(200).body(farmerService.getFarmersByCity(city));
    }

    @GetMapping("/by-experience/{experience}")
    public ResponseEntity<?> getFarmersByExperience(@AuthenticationPrincipal User user, @PathVariable String experience) {
        return ResponseEntity.status(200).body(farmerService.getFarmersByExperience(experience));
    }

    @GetMapping("/by-level/{level}")
    public ResponseEntity<?> getFarmersByLevel(@AuthenticationPrincipal User user, @PathVariable String level) {
        return ResponseEntity.status(200).body(farmerService.getFarmersByLevel(level));
    }

    @GetMapping("/planted/{plantName}")
    public ResponseEntity<?> getFarmersWhoPlantedPlant(@AuthenticationPrincipal User user, @PathVariable String plantName) {
        return ResponseEntity.status(200).body(farmerService.getFarmersWhoPlantedPlant(plantName));
    }
}
