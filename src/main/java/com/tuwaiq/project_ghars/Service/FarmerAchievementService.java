package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Achievement;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.FarmerAchievement;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.AchievementRepository;
import com.tuwaiq.project_ghars.Repository.FarmerAchievementRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerAchievementService {

    private final FarmerAchievementRepository farmerAchievementRepository;
    private final FarmerRepository farmerRepository;
    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public void unlockAchievement(Integer userId, Integer achievementId) {

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

        Achievement achievement = achievementRepository.findAchievementById(achievementId);
        if (achievement == null) {
            throw new ApiException("Achievement not found");
        }

        FarmerAchievement farmerAchievement = new FarmerAchievement();
        farmerAchievement.setUnlockedAt(LocalDateTime.now());
        farmerAchievement.setFarmer(farmer);
        farmerAchievement.setAchievement(achievement);

        farmerAchievementRepository.save(farmerAchievement);
    }

    public List<FarmerAchievement> getMyAchievements(Integer userId) {

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

        return farmerAchievementRepository.findFarmerAchievementByFarmer_Id(farmer.getId());
    }
}
