package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.Level;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.LevelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/level")
@RequiredArgsConstructor
public class LevelController {

    private final LevelService levelService;

    @PostMapping("/add")
    public ResponseEntity<?> addLevel(@AuthenticationPrincipal User user,
                                      @RequestBody @Valid Level level) {

        levelService.addLevel(user.getId(), level);
        return ResponseEntity.status(200).body(new ApiResponse("Level added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllLevels() {
        return ResponseEntity.status(200).body(levelService.getAllLevels());
    }

    @PutMapping("/update/{levelId}")
    public ResponseEntity<?> updateLevel(@AuthenticationPrincipal User user,
                                         @PathVariable Integer levelId,
                                         @RequestBody @Valid Level level) {

        levelService.updateLevel(user.getId(), levelId, level);
        return ResponseEntity.status(200).body(new ApiResponse("Level updated successfully"));
    }

    @DeleteMapping("/delete/{levelId}")
    public ResponseEntity<?> deleteLevel(@AuthenticationPrincipal User user,
                                         @PathVariable Integer levelId) {

        levelService.deleteLevel(user.getId(), levelId);
        return ResponseEntity.status(200).body(new ApiResponse("Level deleted successfully"));
    }
}
