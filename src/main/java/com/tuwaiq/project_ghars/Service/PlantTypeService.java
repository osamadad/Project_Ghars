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
        oldPlantType.setName(plantType.getName());
        oldPlantType.setFamily(plantType.getFamily());
        oldPlantType.setSeason(plantType.getSeason());
        if (!plantType.getCategory().matches("Vegetable| Fruit | Flower | Herb")){
            throw new ApiException("Sorry, the plant category must be Vegetable, Fruit, Flower, or Herb, please try again");
        }
        oldPlantType.setCategory(plantType.getCategory());
        oldPlantType.setGrowthTime(plantType.getGrowthTime());
        if (!plantType.getCategory().matches("Seed | Seedling | Grown")){
            throw new ApiException("Sorry, the farm size must be Seed, Seedling, or Grown, please try again");
        }
        oldPlantType.setType(plantType.getType());
        if (!plantType.getCategory().matches("Kg | Pack")){
            throw new ApiException("Sorry, the farm size must be Kg, or Pack, please try again");
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
}
