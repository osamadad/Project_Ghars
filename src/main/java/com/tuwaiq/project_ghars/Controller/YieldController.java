package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.YieldDTOIn;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.YieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/yield")
@RequiredArgsConstructor
public class YieldController {

    private final YieldService yieldService;

    @PostMapping("/add")
    public ResponseEntity<?> addYield(@AuthenticationPrincipal User user, @RequestBody @Valid YieldDTOIn yieldDTOIn) {
        yieldService.addYield(user.getId(), yieldDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Yield added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllYields() {
        return ResponseEntity.status(200).body(yieldService.getAllYields());
    }

    @GetMapping("/get-by-field/{fieldId}")
    public ResponseEntity<?> getYieldsByField(@AuthenticationPrincipal User user, @PathVariable Integer fieldId) {
        return ResponseEntity.status(200).body(yieldService.getMyYieldsByField(user.getId(), fieldId));
    }

    @PutMapping("/update/{yieldId}")
    public ResponseEntity<?> updateYield(@AuthenticationPrincipal User user, @PathVariable Integer yieldId, @RequestBody @Valid YieldDTOIn yieldDTOIn) {
        yieldService.updateYield(user.getId(), yieldId, yieldDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Yield updated successfully"));
    }

    @DeleteMapping("/delete/{yieldId}")
    public ResponseEntity<?> deleteYield(@AuthenticationPrincipal User user, @PathVariable Integer yieldId) {
        yieldService.deleteYield(user.getId(), yieldId);
        return ResponseEntity.status(200).body(new ApiResponse("Yield deleted successfully"));
    }
}
