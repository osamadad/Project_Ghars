package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FarmerRepositoryTest {

    @Autowired
    FarmerRepository farmerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    LevelRepository levelRepository;
    @Autowired
    FarmRepository farmRepository;
    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    PlantTypeRepository plantTypeRepository;

    User user1, user2, user3;
    Address address1;
    Level level1, level2;
    Farmer farmer1, farmer2, farmer3;
    Farm farm1;
    Field field1;
    PlantType plantType1;
    Farmer farmer;
    List<Farmer> farmers;

    @BeforeEach
    void setUp() {
        user1 = new User(null, "osama", "osama@gmail.com", "osama", "osamadad768", "0500000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        user2 = new User(null, "mohammed", "mohammed@gmail.com", "mohammed", "mohammeddad768", "0550000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        user3 = new User(null, "ahmed", "ahmed@gmail.com", "ahmed", "ahmeddad768", "0555000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        address1 = new Address(null, "Saudi Arabia", "Riyadh", "king street", "1234", "0551394555", user1);
        addressRepository.save(address1);
        level1 = new Level(null, 1, 100, null);
        level2 = new Level(null, 10, 1000, null);
        levelRepository.save(level1);
        levelRepository.save(level2);
        farmer1 = new Farmer(null, "beginner", 1000, 500, 200, level2, null, user1, null, null);
        farmer2 = new Farmer(null, "advanced", 150, 200, 200, level1, null, user2, null, null);
        farmer3 = new Farmer(null, "beginner", 100, 350, 150, level1, null, user3, null, null);
        farmerRepository.save(farmer1);
        farmerRepository.save(farmer2);
        farmerRepository.save(farmer3);
        farm1 = new Farm(null, "123456789", "Pending", "happy farm", "happy farm", "Small", "apples", 5.5, "https", LocalDateTime.now(), farmer3, null, null, null);
        farmRepository.save(farm1);
        plantType1 = new PlantType(null, "Apple", "Malus domestica", "Rosaceae", "fruit", "perennial", "Central Asia", "normal", "2 years", "large", "medium", "high", "soil", "outdoor", "spring", "medium", "pests,disease,temperature_stress", "Grown", "PIECE", null, null);
        plantTypeRepository.save(plantType1);
        field1 = new Field(null, "Harvested", 55.55, LocalDateTime.now(), LocalDateTime.now(), farm1, new HashSet<>(), null);
        field1.getPlantTypes().add(plantType1);
        fieldRepository.save(field1);
    }

    @Test
    public void findFarmerById() {
        farmer = farmerRepository.findFarmerById(farmer1.getId());
        Assertions.assertThat(farmer.getId().equals(farmer1.getId()));
    }

    @Test
    public void findFarmerByUser_Address_City() {
        farmers = farmerRepository.findFarmerByUser_Address_City("Riyadh");
        Assertions.assertThat(farmers.get(0).getUser().equals(user1));
    }

    @Test
    public void findFarmerByFarmerRank() {
        farmers = farmerRepository.findFarmerByFarmerRank("beginner");
        Assertions.assertThat(farmers.get(0).equals(farmer1));
    }

    @Test
    public void findFarmerByMinAndMaxLevels() {
        farmers = farmerRepository.findFarmerByMinAndMaxLevels(1, 2);
        Assertions.assertThat(farmers.get(0).equals(farmer2));
    }

    @Test
    public void findMostExperiencedFarmer() {
        farmers = farmerRepository.findMostExperiencedFarmer();
        Assertions.assertThat(farmers.get(0).equals(farmer1));
    }

    @Test
    public void getFarmerWhoPlantedThisPlant() {
        farmers = farmerRepository.getFarmerWhoPlantedThisPlant("Apple");
        Assertions.assertThat(farmers.get(0).equals(farmer3));
    }

    @Test
    public void findFarmerWithTheMostYield() {
        farmers = farmerRepository.findFarmerWithTheMostYield();
        Assertions.assertThat(farmers.get(0).equals(farmer1));
    }
}
