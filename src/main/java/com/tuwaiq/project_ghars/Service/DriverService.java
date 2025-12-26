package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.DriverDTOIn;
import com.tuwaiq.project_ghars.Model.Driver;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.DriverRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerDriver(DriverDTOIn driverDTOIn) {

        if (userRepository.findUserByUsername(driverDTOIn.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }

        if (userRepository.findUserByEmail(driverDTOIn.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        if (userRepository.findUserByPhoneNumber(driverDTOIn.getPhoneNumber()) != null) {
            throw new ApiException("Phone number already exists");
        }

        User user = new User();
        user.setUsername(driverDTOIn.getUsername());
        user.setPassword(passwordEncoder.encode(driverDTOIn.getPassword()));
        user.setName(driverDTOIn.getName());
        user.setEmail(driverDTOIn.getEmail());
        user.setPhoneNumber(driverDTOIn.getPhoneNumber());
        user.setRole("DRIVER");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Driver driver = new Driver();
        driver.setUser(user);
        driver.setIsBusy(false);

        driverRepository.save(driver);
    }

    public Driver getMyDriver(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("DRIVER")) {
            throw new ApiException("Access denied");
        }

        Driver driver = driverRepository.findDriverById(userId);
        if (driver == null) {
            throw new ApiException("Driver not found");
        }

        return driver;
    }

    public void updateMyDriver(Integer userId, DriverDTOIn driverDTOIn) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("DRIVER")) {
            throw new ApiException("Access denied");
        }

        Driver driver = driverRepository.findDriverById(userId);
        if (driver == null) {
            throw new ApiException("Driver not found");
        }

        User checkUsername = userRepository.findUserByUsername(driverDTOIn.getUsername());
        if (checkUsername != null && !checkUsername.getId().equals(userId)) {
            throw new ApiException("Username already exists");
        }

        User checkEmail = userRepository.findUserByEmail(driverDTOIn.getEmail());
        if (checkEmail != null && !checkEmail.getId().equals(userId)) {
            throw new ApiException("Email already exists");
        }

        User checkPhone = userRepository.findUserByPhoneNumber(driverDTOIn.getPhoneNumber());
        if (checkPhone != null && !checkPhone.getId().equals(userId)) {
            throw new ApiException("Phone number already exists");
        }

        user.setUsername(driverDTOIn.getUsername());
        user.setPassword(passwordEncoder.encode(driverDTOIn.getPassword()));
        user.setName(driverDTOIn.getName());
        user.setEmail(driverDTOIn.getEmail());
        user.setPhoneNumber(driverDTOIn.getPhoneNumber());

        userRepository.save(user);
        driverRepository.save(driver);
    }

    public void deleteMyDriver(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("DRIVER")) {
            throw new ApiException("Access denied");
        }

        Driver driver = driverRepository.findDriverById(userId);
        if (driver == null) {
            throw new ApiException("Driver not found");
        }

        driverRepository.delete(driver);
        userRepository.delete(user);
    }
}
