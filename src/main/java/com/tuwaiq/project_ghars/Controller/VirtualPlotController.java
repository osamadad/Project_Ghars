package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Model.VirtualPlot;
import com.tuwaiq.project_ghars.Service.VirtualPlotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/virtual-plot")
@RequiredArgsConstructor
public class VirtualPlotController {

    private final VirtualPlotService virtualPlotService;

    @PostMapping("/add/{virtualFarmId}/{plotType}")
    public ResponseEntity<?> addVirtualPlot(@AuthenticationPrincipal User user, @PathVariable Integer virtualFarmId, @PathVariable String plotType) {
        virtualPlotService.addVirtualPlot(user.getId(), virtualFarmId, plotType);
        return ResponseEntity.status(200).body(new ApiResponse("Virtual plot added"));
    }

    @GetMapping("/get/{virtualFarmId}")
    public ResponseEntity<?> getMyVirtualPlots(@AuthenticationPrincipal User user, @PathVariable Integer virtualFarmId) {
        return ResponseEntity.status(200).body(virtualPlotService.getMyVirtualPlots(user.getId(), virtualFarmId));
    }

    @GetMapping("/get-by-id/{virtualPlotId}")
    public ResponseEntity<?> getMyVirtualPlotById(@AuthenticationPrincipal User user, @PathVariable Integer virtualPlotId) {
        return ResponseEntity.status(200).body(virtualPlotService.getMyVirtualPlotById(user.getId(), virtualPlotId));
    }

    @DeleteMapping("/delete/{virtualPlotId}")
    public ResponseEntity<?> deleteVirtualPlot(@AuthenticationPrincipal User user, @PathVariable Integer virtualPlotId) {
        virtualPlotService.deleteVirtualPlot(user.getId(), virtualPlotId);
        return ResponseEntity.status(200).body(new ApiResponse("Virtual plot deleted"));
    }

    @PutMapping("/assign-plant/{plotId}/{plantId}")
    public ResponseEntity<?> assignPlantToVirtualPlot(@AuthenticationPrincipal User user, @PathVariable Integer plotId, @PathVariable Integer plantId) {
        virtualPlotService.assignAPlantToVirtualPlot(user.getId(), plotId, plantId);
        return ResponseEntity.status(200).body(new ApiResponse("Plant assigned to virtual plot successfully"));
    }

    @PutMapping("/uproot/{plotId}")
    public ResponseEntity<?> uprootPlantFromVirtualPlot(@AuthenticationPrincipal User user, @PathVariable Integer plotId) {
        virtualPlotService.upRootPlantInVirtualPlot(user.getId(), plotId);
        return ResponseEntity.status(200).body(new ApiResponse("Plant uprooted from virtual plot successfully"));
    }
}
