package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Model.VirtualFarm;
import com.tuwaiq.project_ghars.Service.VirtualFarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/virtualfarm")
@RequiredArgsConstructor
public class VirtualFarmController {

    private final VirtualFarmService virtualFarmService;

    @PostMapping("/add")
    public ResponseEntity<?> addVirtualFarm(@AuthenticationPrincipal User user, @RequestBody VirtualFarm virtualFarm) {
        virtualFarmService.addVirtualFarm(user.getId(), virtualFarm);
        return ResponseEntity.status(200).body(new ApiResponse("Virtual farm added"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMyVirtualFarms(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200)
                .body(virtualFarmService.getMyVirtualFarms(user.getId()));
    }

    @GetMapping("/get/{virtualFarmId}")
    public ResponseEntity<?> getMyVirtualFarmById(@AuthenticationPrincipal User user,
                                                  @PathVariable Integer virtualFarmId) {
        return ResponseEntity.status(200)
                .body(virtualFarmService.getMyVirtualFarmById(user.getId(), virtualFarmId));
    }

    @PutMapping("/update/{virtualFarmId}")
    public ResponseEntity<?> updateVirtualFarm(@AuthenticationPrincipal User user,
                                               @PathVariable Integer virtualFarmId,
                                               @RequestBody VirtualFarm virtualFarm) {
        virtualFarmService.updateVirtualFarm(user.getId(), virtualFarmId, virtualFarm);
        return ResponseEntity.status(200).body(new ApiResponse("Virtual farm updated"));
    }

    @DeleteMapping("/delete/{virtualFarmId}")
    public ResponseEntity<?> deleteVirtualFarm(@AuthenticationPrincipal User user,
                                               @PathVariable Integer virtualFarmId) {
        virtualFarmService.deleteVirtualFarm(user.getId(), virtualFarmId);
        return ResponseEntity.status(200).body(new ApiResponse("Virtual farm deleted"));
    }
}
