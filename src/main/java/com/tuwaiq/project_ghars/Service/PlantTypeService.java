package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.PlantType;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.PlantTypeRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantTypeService {

    private final PlantTypeRepository plantTypeRepository;
    private final UserRepository userRepository;

    public void addPlantType(Integer userId, PlantType plantType) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        plantTypeRepository.save(plantType);
    }

    public List<PlantType> getAllPlantTypes() {
        return plantTypeRepository.findAll();
    }

    public void updatePlantType(Integer userId, Integer plantTypeId, PlantType plantType) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        PlantType oldPlantType = plantTypeRepository.findPlantTypeById(plantTypeId);
        if (oldPlantType == null) {
            throw new ApiException("Plant type not found");
        }
        oldPlantType.setCommonName(plantType.getCommonName());

        oldPlantType.setScientificName(plantType.getScientificName());

        oldPlantType.setFamily(plantType.getFamily());

        if (!plantType.getCategory().matches("fruit|vegetable|flower|herb")) {
            throw new ApiException(
                    "Sorry, the plant category must be fruit, vegetable, flower, or herb, please try again"
            );
        }
        oldPlantType.setCategory(plantType.getCategory());

        if (!plantType.getLifeSpan().matches("annual|perennial|biennial")) {
            throw new ApiException(
                    "Sorry, the plant life span must be annual, perennial, or biennial, please try again"
            );
        }
        oldPlantType.setLifeSpan(plantType.getLifeSpan());

        oldPlantType.setNativeRegion(plantType.getNativeRegion());

        if (!plantType.getGrowthSpeed().matches("slow|normal|fast")) {
            throw new ApiException(
                    "Sorry, the plant growth speed must be slow, normal, or fast, please try again"
            );
        }
        oldPlantType.setGrowthSpeed(plantType.getGrowthSpeed());

        if (!plantType.getExpectedTimeToGrow().matches("^[0-9]+\\s(days|months|years)$")) {
            throw new ApiException(
                    "Sorry, the expected time to grow must be in this format: number + days, months, or years, please try again"
            );
        }
        oldPlantType.setExpectedTimeToGrow(plantType.getExpectedTimeToGrow());

        if (!plantType.getSize().matches("small|medium|large")) {
            throw new ApiException(
                    "Sorry, the plant size must be small, medium, or large, please try again"
            );
        }
        oldPlantType.setSize(plantType.getSize());

        if (!plantType.getWaterNeeds().matches("low|medium|high")) {
            throw new ApiException(
                    "Sorry, the plant water needs must be low, medium, or high, please try again"
            );
        }
        oldPlantType.setWaterNeeds(plantType.getWaterNeeds());

        if (!plantType.getSunNeeds().matches("low|medium|high")) {
            throw new ApiException(
                    "Sorry, the plant sun needs must be low, medium, or high, please try again"
            );
        }
        oldPlantType.setSunNeeds(plantType.getSunNeeds());

        if (!plantType.getGrowingMedium().matches("soil|water|both")) {
            throw new ApiException(
                    "Sorry, the growing medium must be soil, water, or both, please try again"
            );
        }
        oldPlantType.setGrowingMedium(plantType.getGrowingMedium());

        if (!plantType.getPlantingPlace().matches("indoor|outdoor|both")) {
            throw new ApiException(
                    "Sorry, the planting place must be indoor, outdoor, or both, please try again"
            );
        }
        oldPlantType.setPlantingPlace(plantType.getPlantingPlace());
        if (!plantType.getSeason().matches("winter|spring|summer|autumn")) {
            throw new ApiException(
                    "Sorry, the plant season must be winter, spring, summer, or autumn, please try again"
            );
        }
        oldPlantType.setSeason(plantType.getSeason());
        if (!plantType.getDifficultyLevel().matches("easy|medium|hard")) {
            throw new ApiException(
                    "Sorry, the difficulty level must be easy, medium, or hard, please try again"
            );
        }
        oldPlantType.setDifficultyLevel(plantType.getDifficultyLevel());

        if (!plantType.getCommonRisks().matches(
                "^(overwatering|pests|disease|temperature_stress)(,(overwatering|pests|disease|temperature_stress))*$")) {
            throw new ApiException(
                    "Sorry, the common risks must be one or more of: overwatering, pests, disease, or temperature stress, please try again"
            );
        }
        oldPlantType.setCommonRisks(plantType.getCommonRisks());

        if (!plantType.getType().matches("Seed|Seedling|Grown")) {
            throw new ApiException(
                    "Sorry, the farm size must be Seed, Seedling, or Grown, please try again"
            );
        }
        oldPlantType.setType(plantType.getType());

        if (!plantType.getUnit().matches("PIECE|PACK|BUNCH")) {
            throw new ApiException(
                    "Sorry, the farm size must be PIECE, PACK, or BUNCH, please try again"
            );
        }

        oldPlantType.setUnit(plantType.getUnit());

        plantTypeRepository.save(oldPlantType);
    }

    public void deletePlantType(Integer userId, Integer plantTypeId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        PlantType plantType = plantTypeRepository.findPlantTypeById(plantTypeId);
        if (plantType == null) {
            throw new ApiException("Plant type not found");
        }

        plantTypeRepository.delete(plantType);
    }

    public List<PlantType> getPlantTypesByFamily(Integer userId, String family) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByFamily(family);
    }

    public List<PlantType> getPlantTypesByCategory(Integer userId, String category) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByCategory(category);
    }

    public List<PlantType> getPlantTypesBySize(Integer userId, String size) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeBySize(size);
    }

    public List<PlantType> getPlantTypesByGrowthSpeed(Integer userId, String growthSpeed) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByGrowthSpeed(growthSpeed);
    }

    public List<PlantType> getPlantTypesByWaterNeeds(Integer userId, String waterNeeds) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByWaterNeeds(waterNeeds);
    }

    public List<PlantType> getPlantTypesBySunNeeds(Integer userId, String sunNeeds) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeBySunNeeds(sunNeeds);
    }

    public List<PlantType> getPlantTypesBySeason(Integer userId, String season) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeBySeason(season);
    }

    public List<PlantType> getPlantTypesByDifficultyLevel(Integer userId, String difficultyLevel) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByDifficultyLevel(difficultyLevel);
    }

    public List<PlantType> getPlantTypesByGrowingMedium(Integer userId, String growingMedium) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByGrowingMedium(growingMedium);
    }

    public List<PlantType> getPlantTypesByPlantingPlace(Integer userId, String plantingPlace) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByPlantingPlace(plantingPlace);
    }

    private void checkUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
    }

    public List<PlantType> getPlantTypesByLifeSpan(Integer userId, String lifeSpan) {
        checkUser(userId);
        return plantTypeRepository.findPlantTypeByLifeSpan(lifeSpan);
    }

}
