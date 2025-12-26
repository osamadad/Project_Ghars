package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Achievement;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.AchievementRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    public void addAchievement(Integer userId, Achievement achievement) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        achievement.setId(null);
        achievement.setFarmer(farmer);
        achievement.setIsCompleted(false);

        achievementRepository.save(achievement);
    }

    public List<Achievement> getMyAchievements(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        return achievementRepository.findAchievementByFarmer(farmer);
    }

    public Achievement getMyAchievementById(Integer userId, Integer achievementId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Achievement achievement = achievementRepository.findAchievementById(achievementId);
        if (achievement == null) {
            throw new ApiException("Achievement not found");
        }

        if (!achievement.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to access this achievement");
        }

        return achievement;
    }

    public void updateAchievement(Integer userId, Integer achievementId, Achievement achievementRequest) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Achievement achievement = achievementRepository.findAchievementById(achievementId);
        if (achievement == null) {
            throw new ApiException("Achievement not found");
        }

        if (!achievement.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to update this achievement");
        }

        achievement.setTitle(achievementRequest.getTitle());
        achievement.setTask(achievementRequest.getTask());

        if (achievementRequest.getIsCompleted() != null) {
            achievement.setIsCompleted(achievementRequest.getIsCompleted());
        }

        achievementRepository.save(achievement);
    }

    public void completeAchievement(Integer userId, Integer achievementId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Achievement achievement = achievementRepository.findAchievementById(achievementId);
        if (achievement == null) {
            throw new ApiException("Achievement not found");
        }

        if (!achievement.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to update this achievement");
        }

        achievement.setIsCompleted(true);

        achievementRepository.save(achievement);
    }

    public void deleteAchievement(Integer userId, Integer achievementId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Achievement achievement = achievementRepository.findAchievementById(achievementId);
        if (achievement == null) {
            throw new ApiException("Achievement not found");
        }

        if (!achievement.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to delete this achievement");
        }

        achievementRepository.delete(achievement);
    }
}
