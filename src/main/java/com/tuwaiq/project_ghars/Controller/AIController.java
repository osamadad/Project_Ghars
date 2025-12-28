package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.AIService;
import com.tuwaiq.project_ghars.Service.PlantNetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/va/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService ;
    private final PlantNetService plantNetService;

    @GetMapping("/soil-seeds")
    public ResponseEntity<?> soilSeeds(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.soilAndSeeds(user.getId()));
    }

    @GetMapping("/home-gardening")
    public ResponseEntity<?> homeGardening(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.homeGardening(user.getId()));
    }

    @GetMapping("/watering-fertilizing")
    public ResponseEntity<?> wateringFertilizing(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.wateringAndFertilizing(user.getId()));
    }

    @GetMapping("/plant-care")
    public ResponseEntity<?> plantCare(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.plantCare(user.getId()));
    }

    @GetMapping("/plant-problems")
    public ResponseEntity<?> plantProblems(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.plantProblems(user.getId()));
    }

    @GetMapping("/recommend-event")
    public ResponseEntity<?> recommendEvent(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(aiService.recommendBestEvent(user.getId()));
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
