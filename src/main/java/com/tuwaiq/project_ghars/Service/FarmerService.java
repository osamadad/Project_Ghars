package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Config.Configuration;
import com.tuwaiq.project_ghars.DTOIn.FarmerDTOIn;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;
    private final Configuration configuration ;

    public void registerFarmer(FarmerDTOIn farmerDTOIn) {

        if (userRepository.findUserByUsername(farmerDTOIn.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }

        if (userRepository.findUserByEmail(farmerDTOIn.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        if (userRepository.findUserByPhoneNumber(farmerDTOIn.getPhoneNumber()) != null) {
            throw new ApiException("Phone number already exists");
        }

        User user = new User();
        user.setUsername(farmerDTOIn.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(farmerDTOIn.getPassword()));
        user.setName(farmerDTOIn.getName());
        user.setEmail(farmerDTOIn.getEmail());
        user.setPhoneNumber(farmerDTOIn.getPhoneNumber());
        user.setRole("FARMER");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Farmer farmer = new Farmer();
        farmer.setUser(user);
        farmer.setExperience(farmerDTOIn.getExperience());
        farmer.setLevel(farmerDTOIn.getLevel());

        farmerRepository.save(farmer);
    }

    public List<Farmer> getAllFarmer(){
        return farmerRepository.findAll();
    }

    public Farmer getMyFarmer(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        return farmer;
    }

    public void updateMyFarmer(Integer userId, FarmerDTOIn farmerDTOin) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        User checkUsername = userRepository.findUserByUsername(farmerDTOin.getUsername());
        if (checkUsername != null && !checkUsername.getId().equals(userId)) {
            throw new ApiException("Username already exists");
        }

        User checkEmail = userRepository.findUserByEmail(farmerDTOin.getEmail());
        if (checkEmail != null && !checkEmail.getId().equals(userId)) {
            throw new ApiException("Email already exists");
        }

        User checkPhone = userRepository.findUserByPhoneNumber(farmerDTOin.getPhoneNumber());
        if (checkPhone != null && !checkPhone.getId().equals(userId)) {
            throw new ApiException("Phone number already exists");
        }

        user.setUsername(farmerDTOin.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(farmerDTOin.getPassword()));
        user.setName(farmerDTOin.getName());
        user.setEmail(farmerDTOin.getEmail());
        user.setPhoneNumber(farmerDTOin.getPhoneNumber());

        farmer.setExperience(farmerDTOin.getExperience());
        farmer.setLevel(farmerDTOin.getLevel());

        userRepository.save(user);
        farmerRepository.save(farmer);
    }

    public void deleteMyFarmer(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        farmerRepository.delete(farmer);
        userRepository.delete(user);
    }

    public List<Farmer> getFarmersByCity(String city) {
        return farmerRepository.findFarmerByUser_Address_City(city);
    }

    public List<Farmer> getFarmersByExperience(String experience) {
        return farmerRepository.findFarmerByExperience(experience);
    }

    public List<Farmer> getFarmersByLevel(String level) {
        return farmerRepository.findFarmerByLevel(level);
    }

    public List<Farmer> getFarmersWhoPlantedPlant(String plantName) {
        return farmerRepository.getFarmerWhoPlantedThisPlant(plantName);
    }
}
