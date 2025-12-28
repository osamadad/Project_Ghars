package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.FarmDTOIn;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farm")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping("/add")
    public ResponseEntity<?> addFarm(@AuthenticationPrincipal User user, @RequestBody @Valid FarmDTOIn farmDTOIn) {
        farmService.addFarm(user.getId(), farmDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Farm added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllFarms() {
        return ResponseEntity.status(200).body(farmService.getAllFarms());
    }

    @GetMapping("/get-my-farm")
    public ResponseEntity<?> getMyFarm(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(farmService.getMyFarm(user.getId()));
    }

    @PutMapping("/update/{farmId}")
    public ResponseEntity<?> updateFarm(@AuthenticationPrincipal User user, @PathVariable Integer farmId, @RequestBody @Valid FarmDTOIn farmDTOIn) {
        farmService.updateFarm(user.getId(), farmId, farmDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Farm updated successfully"));
    }

    @DeleteMapping("/delete/{farmId}")
    public ResponseEntity<?> deleteFarm(@AuthenticationPrincipal User user, @PathVariable Integer farmId) {
        farmService.deleteFarm(user.getId(), farmId);
        return ResponseEntity.status(200).body(new ApiResponse("Farm deleted successfully"));
    }


    @PutMapping("/license/accept/{farmId}")
    public ResponseEntity<?> acceptLicense(@AuthenticationPrincipal User user,
                                           @PathVariable Integer farmId) {

        farmService.acceptLicense(user.getId(), farmId);
        return ResponseEntity.status(200).body(new ApiResponse("Farm license accepted"));
    }

    @PutMapping("/license/reject/{farmId}")
    public ResponseEntity<?> rejectLicense(@AuthenticationPrincipal User user, @PathVariable Integer farmId) {

        farmService.rejectLicense(user.getId(), farmId);
        return ResponseEntity.status(200).body(new ApiResponse("Farm license rejected"));
    }


}
