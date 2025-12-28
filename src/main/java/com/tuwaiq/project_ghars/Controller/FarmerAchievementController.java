package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.FarmerAchievementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farmer-achievement")
@RequiredArgsConstructor
public class FarmerAchievementController {

    private final FarmerAchievementService farmerAchievementService;

    @PostMapping("/unlock/{achievementId}")
    public ResponseEntity<?> unlockAchievement(@AuthenticationPrincipal User user, @PathVariable Integer achievementId) {

        farmerAchievementService.unlockAchievement(user.getId(), achievementId);
        return ResponseEntity.status(200).body(new ApiResponse("Achievement unlocked successfully"));
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyAchievements(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(farmerAchievementService.getMyAchievements(user.getId()));
    }
}
