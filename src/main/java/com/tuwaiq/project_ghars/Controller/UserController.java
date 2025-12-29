package com.tuwaiq.project_ghars.Controller;


import com.tuwaiq.project_ghars.Api.ApiResponse;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService ;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid User user) {
        userService.adminRegister(user);
        return ResponseEntity.status(200).body(new ApiResponse("Admin registered"));
    }
}
