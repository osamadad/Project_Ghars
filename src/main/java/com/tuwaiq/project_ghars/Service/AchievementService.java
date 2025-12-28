package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Achievement;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.AchievementRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public void addAchievement(Integer userId, Achievement achievement) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }
        achievementRepository.save(achievement);
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    public void updateAchievement(Integer userId, Integer achievementId, Achievement achievement) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Achievement oldAchievement = achievementRepository.findAchievementById(achievementId);
        if (oldAchievement == null) {
            throw new ApiException("Achievement not found");
        }

        oldAchievement.setTitle(achievement.getTitle());
        oldAchievement.setTask(achievement.getTask());

        achievementRepository.save(oldAchievement);
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

        achievementRepository.delete(achievement);
    }
}
