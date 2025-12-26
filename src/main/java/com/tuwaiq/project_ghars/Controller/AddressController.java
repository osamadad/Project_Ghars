package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.AddressDTOIn;
import com.tuwaiq.project_ghars.Model.Address;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<?> createAddress(@AuthenticationPrincipal User user, @RequestBody @Valid AddressDTOIn addressDTOIn) {
        addressService.addAddress(user.getId(),addressDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Address added successfully"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllAddresses(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(addressService.getAllAddresses());
    }

    @GetMapping("/get-my-address")
    public ResponseEntity<?> getMyAddress(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(addressService.getMyAddress(user.getId()));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@AuthenticationPrincipal User user, @RequestBody @Valid AddressDTOIn addressDTOIn) {
        addressService.updateAddress(user.getId(),addressDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Address updated successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAddress(@AuthenticationPrincipal User user) {
        addressService.deleteAddress(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Address deleted successfully"));
    }
}
