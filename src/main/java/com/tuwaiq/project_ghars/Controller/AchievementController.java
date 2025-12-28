package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Achievement;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/achievement")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @PostMapping("/add")
    public ResponseEntity<?> addAchievement(@AuthenticationPrincipal User user,
                                            @RequestBody @Valid Achievement achievement) {

        achievementService.addAchievement(user.getId(), achievement);
        return ResponseEntity.status(200).body(new ApiResponse("Achievement added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllAchievements() {
        return ResponseEntity.status(200).body(achievementService.getAllAchievements());
    }

    @PutMapping("/update/{achievementId}")
    public ResponseEntity<?> updateAchievement(@AuthenticationPrincipal User user,
                                               @PathVariable Integer achievementId,
                                               @RequestBody @Valid Achievement achievement) {

        achievementService.updateAchievement(user.getId(), achievementId, achievement);
        return ResponseEntity.status(200).body(new ApiResponse("Achievement updated successfully"));
    }

    @DeleteMapping("/delete/{achievementId}")
    public ResponseEntity<?> deleteAchievement(@AuthenticationPrincipal User user,
                                               @PathVariable Integer achievementId) {

        achievementService.deleteAchievement(user.getId(), achievementId);
        return ResponseEntity.status(200).body(new ApiResponse("Achievement deleted successfully"));
    }
}
