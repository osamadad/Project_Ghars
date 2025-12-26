package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.DriverDTOIn;
import com.tuwaiq.project_ghars.Model.Driver;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/register")
    public ResponseEntity<?> registerDriver(@RequestBody @Valid DriverDTOIn driverDTOIn) {
        driverService.registerDriver(driverDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Driver registered"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMyDriver(@AuthenticationPrincipal User user) {
        Driver driver = driverService.getMyDriver(user.getId());
        return ResponseEntity.status(200).body(driver);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMyDriver(@AuthenticationPrincipal User user, @RequestBody @Valid DriverDTOIn driverDTOIn) {
        driverService.updateMyDriver(user.getId(), driverDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Driver updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyDriver(@AuthenticationPrincipal User user) {
        driverService.deleteMyDriver(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Driver deleted"));
    }
}
