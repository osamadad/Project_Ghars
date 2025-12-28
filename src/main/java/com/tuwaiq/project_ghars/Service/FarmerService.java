package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Config.Configuration;
import com.tuwaiq.project_ghars.DTOIn.FarmerDTOIn;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.FarmerAchievement;
import com.tuwaiq.project_ghars.Model.Level;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.FarmerAchievementRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.LevelRepository;
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
    private final Configuration configuration;
    private final LevelRepository levelRepository;
    private final FarmerAchievementRepository farmerAchievementRepository;

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

        Level level = levelRepository.findLevelById(farmerDTOIn.getLevelId());
        if (level == null) {
            throw new ApiException("Level not found");
        }

        FarmerAchievement farmerAchievement =
                farmerAchievementRepository.findFarmerAchievementById(farmerDTOIn.getFarmerAchievementId());
        if (farmerAchievement == null) {
            throw new ApiException("FarmerAchievement not found");
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

        farmer.setFarmerRank(farmerDTOIn.getFarmerRank());
        farmer.setFarmerExperience(farmerDTOIn.getFarmerExperience());
        farmer.setTotalYield(farmerDTOIn.getTotalYield());
        farmer.setSeasonalYield(farmerDTOIn.getSeasonalYield());

        farmer.setLevel(level);
        farmer.setFarmerAchievement(farmerAchievement);

        farmerRepository.save(farmer);
    }

    public List<Farmer> getAllFarmer() {
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

    public void updateMyFarmer(Integer userId, FarmerDTOIn farmerDTOIn) {

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

        User checkUsername = userRepository.findUserByUsername(farmerDTOIn.getUsername());
        if (checkUsername != null && !checkUsername.getId().equals(userId)) {
            throw new ApiException("Username already exists");
        }

        User checkEmail = userRepository.findUserByEmail(farmerDTOIn.getEmail());
        if (checkEmail != null && !checkEmail.getId().equals(userId)) {
            throw new ApiException("Email already exists");
        }

        User checkPhone = userRepository.findUserByPhoneNumber(farmerDTOIn.getPhoneNumber());
        if (checkPhone != null && !checkPhone.getId().equals(userId)) {
            throw new ApiException("Phone number already exists");
        }

        Level level = levelRepository.findLevelById(farmerDTOIn.getLevelId());
        if (level == null) {
            throw new ApiException("Level not found");
        }

        FarmerAchievement farmerAchievement =
                farmerAchievementRepository.findFarmerAchievementById(farmerDTOIn.getFarmerAchievementId());
        if (farmerAchievement == null) {
            throw new ApiException("FarmerAchievement not found");
        }

        user.setUsername(farmerDTOIn.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(farmerDTOIn.getPassword()));
        user.setName(farmerDTOIn.getName());
        user.setEmail(farmerDTOIn.getEmail());
        user.setPhoneNumber(farmerDTOIn.getPhoneNumber());

        farmer.setFarmerRank(farmerDTOIn.getFarmerRank());
        farmer.setFarmerExperience(farmerDTOIn.getFarmerExperience());
        farmer.setTotalYield(farmerDTOIn.getTotalYield());
        farmer.setSeasonalYield(farmerDTOIn.getSeasonalYield());

        farmer.setLevel(level);
        farmer.setFarmerAchievement(farmerAchievement);

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

    public List<Farmer> getFarmersByExperience(Integer farmerExperience) {
        return farmerRepository.findFarmerByFarmerExperience(farmerExperience);
    }

    public List<Farmer> getFarmersByRank(String farmerRank) {
        return farmerRepository.findFarmerByFarmerRank(farmerRank);
    }

    public List<Farmer> getFarmersByLevel(Integer levelId) {
        return farmerRepository.findFarmerByLevel_Id(levelId);
    }

    public List<Farmer> getFarmersWhoPlantedPlant(String plantName) {
        return farmerRepository.getFarmerWhoPlantedThisPlant(plantName);
    }
}
