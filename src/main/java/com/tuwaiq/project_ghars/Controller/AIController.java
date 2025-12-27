package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/va/ai")
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService ;

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
}
