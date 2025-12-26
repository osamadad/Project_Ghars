package com.tuwaiq.project_ghars.Controller;

import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.DTOIn.CustomerDTOIn;
import com.tuwaiq.project_ghars.Model.Customer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody @Valid CustomerDTOIn customerDTOIn) {
        customerService.registerCustomer(customerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer registered"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMyCustomer(@AuthenticationPrincipal User user) {
        Customer customer = customerService.getMyCustomer(user.getId());
        return ResponseEntity.status(200).body(customer);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMyCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTOIn customerDTOIn) {
        customerService.updateMyCustomer(user.getId(), customerDTOIn);
        return ResponseEntity.status(200).body(new ApiResponse("Customer updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMyCustomer(@AuthenticationPrincipal User user) {
        customerService.deleteMyCustomer(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Customer deleted"));
    }
}
