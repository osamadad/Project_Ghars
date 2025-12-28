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

    @GetMapping("/by-rank/{rank}")
    public ResponseEntity<?> getFarmersByRank(@AuthenticationPrincipal User user, @PathVariable String rank) {
        return ResponseEntity.status(200).body(farmerService.getFarmersByRank(rank));
    }

    @GetMapping("/by-level/{minLevel}/{maxLevel}")
    public ResponseEntity<?> getFarmersByMinAndMaxLevel(@AuthenticationPrincipal User user, @PathVariable Integer minLevel, @PathVariable Integer maxLevel) {
        return ResponseEntity.status(200).body(farmerService.getFarmersByLevel(minLevel, maxLevel));
    }

    @GetMapping("/most-level")
    public ResponseEntity<?> getMostExperiencedFarmer(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(farmerService.getMostExperiencedFarmer());
    }

    @GetMapping("/planted/{plantName}")
    public ResponseEntity<?> getFarmersWhoPlantedPlant(@AuthenticationPrincipal User user, @PathVariable String plantName) {
        return ResponseEntity.status(200).body(farmerService.getFarmersWhoPlantedPlant(plantName));
    }

    @GetMapping("/most-yield")
    public ResponseEntity<?> getFarmerWithTheMostYield(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(farmerService.getFarmerWithTheMostYield());
    }

    @PostMapping("/talk/{farmerId}/{message}")
    public ResponseEntity<?> talkWithFarmer(@AuthenticationPrincipal User user, @PathVariable Integer farmerId, @PathVariable String message) {
        farmerService.talkWithFarmers(user.getId(), farmerId, message);
        return ResponseEntity.status(200).body(new ApiResponse("Message sent successfully"));
    }

    @PostMapping("/talk-about-plant/{farmerId}/{plantName}")
    public ResponseEntity<?> talkWithFarmersWhoPlantedAPlant(@AuthenticationPrincipal User user, @PathVariable Integer farmerId, @PathVariable String plantName) {
        farmerService.talkWithFarmersWhoPlantedAPlant(user.getId(), farmerId, plantName);
        return ResponseEntity.status(200).body(new ApiResponse("Message sent successfully"));
    }
}
