package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@EntityScan(basePackages = "com.tuwaiq.project_ghars.Model")
public class PlantTypeRepositoryTest {

    @Autowired
    PlantTypeRepository plantTypeRepository;

    PlantType plantType1, plantType2, plantType3;
    List<PlantType> plantTypes;

    @BeforeEach
    void setUp() {

        plantType1 = new PlantType(null, "Apple", "Malus domestica", "Rosaceae", "fruit", "perennial", "Central Asia", "normal", "2 years", "large", "medium", "high", "soil", "outdoor", "spring", "medium", "pests,disease", null, null);

        plantType2 = new PlantType(null, "Tomato", "Solanum lycopersicum", "Solanaceae", "vegetable", "annual", "South America", "fast", "90 days", "medium", "high", "high", "soil", "outdoor", "summer", "easy", "overwatering,pests",  null, null);

        plantType3 = new PlantType(null, "Basil", "Ocimum basilicum", "Lamiaceae", "herb", "annual", "Africa", "normal", "60 days", "small", "medium", "medium", "soil", "both", "spring", "easy", "pests", null, null);

        plantTypeRepository.save(plantType1);
        plantTypeRepository.save(plantType2);
        plantTypeRepository.save(plantType3);
    }

    @Test
    public void findPlantTypeById() {
        PlantType result = plantTypeRepository.findPlantTypeById(plantType1.getId());
        Assertions.assertThat(result.getId()).isEqualTo(plantType1.getId());
    }

    @Test
    public void findPlantTypeBySeasonIgnoreCase() {
        plantTypes = plantTypeRepository.findPlantTypeBySeasonIgnoreCase("SPRING");
        Assertions.assertThat(plantTypes).contains(plantType1, plantType3);
    }

    @Test
    public void findPlantTypeByFamily() {
        plantTypes = plantTypeRepository.findPlantTypeByFamily("Solanaceae");
        Assertions.assertThat(plantTypes.get(0)).isEqualTo(plantType2);
    }

    @Test
    public void findPlantTypeByCategory() {
        plantTypes = plantTypeRepository.findPlantTypeByCategory("herb");
        Assertions.assertThat(plantTypes.get(0)).isEqualTo(plantType3);
    }

    @Test
    public void findPlantTypeBySize() {
        plantTypes = plantTypeRepository.findPlantTypeBySize("large");
        Assertions.assertThat(plantTypes.get(0)).isEqualTo(plantType1);
    }

    @Test
    public void findPlantTypeByGrowthSpeed() {
        plantTypes = plantTypeRepository.findPlantTypeByGrowthSpeed("fast");
        Assertions.assertThat(plantTypes.get(0)).isEqualTo(plantType2);
    }

    @Test
    public void findPlantTypeByWaterNeeds() {
        plantTypes = plantTypeRepository.findPlantTypeByWaterNeeds("medium");
        Assertions.assertThat(plantTypes).contains(plantType1, plantType3);
    }

    @Test
    public void findPlantTypeBySunNeeds() {
        plantTypes = plantTypeRepository.findPlantTypeBySunNeeds("high");
        Assertions.assertThat(plantTypes).contains(plantType1, plantType2);
    }

    @Test
    public void findPlantTypeBySeason() {
        plantTypes = plantTypeRepository.findPlantTypeBySeason("summer");
        Assertions.assertThat(plantTypes.get(0)).isEqualTo(plantType2);
    }

    @Test
    public void findPlantTypeByDifficultyLevel() {
        plantTypes = plantTypeRepository.findPlantTypeByDifficultyLevel("easy");
        Assertions.assertThat(plantTypes).contains(plantType2, plantType3);
    }

    @Test
    public void findPlantTypeByGrowingMedium() {
        plantTypes = plantTypeRepository.findPlantTypeByGrowingMedium("soil");
        Assertions.assertThat(plantTypes).contains(plantType1, plantType2, plantType3);
    }

    @Test
    public void findPlantTypeByPlantingPlace() {
        plantTypes = plantTypeRepository.findPlantTypeByPlantingPlace("outdoor");
        Assertions.assertThat(plantTypes).contains(plantType1, plantType2);
    }

    @Test
    public void findPlantTypeByLifeSpan() {
        plantTypes = plantTypeRepository.findPlantTypeByLifeSpan("annual");
        Assertions.assertThat(plantTypes).contains(plantType2, plantType3);
    }
}
