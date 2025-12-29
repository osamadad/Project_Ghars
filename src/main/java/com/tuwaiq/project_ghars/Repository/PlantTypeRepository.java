package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Address;
import com.tuwaiq.project_ghars.Model.PlantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantTypeRepository extends JpaRepository<PlantType,Integer> {

    PlantType findPlantTypeById(Integer id);

    List<PlantType> findPlantTypeBySeasonIgnoreCase(String season);

    List<PlantType> findPlantTypeByFamily(String family);

    List<PlantType> findPlantTypeByCategory(String category);

    List<PlantType> findPlantTypeBySize(String size);

    List<PlantType> findPlantTypeByGrowthSpeed(String growthSpeed);

    List<PlantType> findPlantTypeByWaterNeeds(String waterNeeds);

    List<PlantType> findPlantTypeBySunNeeds(String sunNeeds);

    List<PlantType> findPlantTypeBySeason(String season);

    List<PlantType> findPlantTypeByDifficultyLevel(String difficultyLevel);

    List<PlantType> findPlantTypeByGrowingMedium(String growingMedium);

    List<PlantType> findPlantTypeByPlantingPlace(String plantingPlace);

    List<PlantType> findPlantTypeByLifeSpan(String lifeSpan);
}
