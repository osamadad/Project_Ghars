package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Config.Configuration;
import com.tuwaiq.project_ghars.Model.Customer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository ;
    private final Configuration configuration ;

    public void adminRegister(User user){

        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }

        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        if (userRepository.findUserByPhoneNumber(user.getPhoneNumber()) != null) {
            throw new ApiException("Phone number already exists");
        }

        User admin = new User();
        admin.setUsername(user.getUsername());
        admin.setPassword(configuration.passwordEncoder().encode(user.getPassword()));
        admin.setName(user.getName());
        admin.setEmail(user.getEmail());
        admin.setPhoneNumber(user.getPhoneNumber());
        admin.setRole("ADMIN");
        admin.setCreatedAt(LocalDateTime.now());

        userRepository.save(admin);


    }


}
