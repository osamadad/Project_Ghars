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
}
