package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Level;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.LevelRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;
    private final UserRepository userRepository;

    public void addLevel(Integer userId, Level level) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
//        if (!user.getRole().equals("ADMIN")) {
//            throw new ApiException("Access denied");
//        }
        if (levelRepository.findLevelByLevelNumber(level.getLevelNumber()) != null) {
            throw new ApiException("Level number already exists");
        }
        levelRepository.save(level);
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAllByOrderByLevelNumberAsc();
    }

    public void updateLevel(Integer userId, Integer levelId, Level level) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Level oldLevel = levelRepository.findLevelById(levelId);
        if (oldLevel == null) {
            throw new ApiException("Level not found");
        }

        Level check = levelRepository.findLevelByLevelNumber(level.getLevelNumber());
        if (check != null && !check.getId().equals(levelId)) {
            throw new ApiException("Level number already exists");
        }

        oldLevel.setLevelNumber(level.getLevelNumber());
        oldLevel.setRequiredExp(level.getRequiredExp());

        levelRepository.save(oldLevel);
    }

    public void deleteLevel(Integer userId, Integer levelId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Access denied");
        }

        Level level = levelRepository.findLevelById(levelId);
        if (level == null) {
            throw new ApiException("Level not found");
        }
        levelRepository.delete(level);
    }
}
