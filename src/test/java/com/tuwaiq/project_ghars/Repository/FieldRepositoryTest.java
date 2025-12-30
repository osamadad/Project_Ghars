package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.Field;
import com.tuwaiq.project_ghars.Model.User;
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
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@EntityScan(basePackages = "com.tuwaiq.project_ghars.Model")
public class FieldRepositoryTest {

    @Autowired
    FieldRepository fieldRepository;

    @Autowired
    FarmRepository farmRepository;

    @Autowired
    FarmerRepository farmerRepository;

    @Autowired
    UserRepository userRepository;

    User user1, user2;
    Farmer farmer1, farmer2;
    Farm farm1, farm2;
    Field field1, field2;

    @BeforeEach
    void setUp() {

        user1 = new User(null, "osama", "osama@gmail.com", "osama", "osamadad768", "0500000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        user2 = new User(null, "ahmed", "ahmed@gmail.com", "ahmed", "ahmeddad768", "0550000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        userRepository.save(user1);
        userRepository.save(user2);
        farmer1 = new Farmer(null, "beginner", 100, 200, 300, null, null, user1, null, null);
        farmer2 = new Farmer(null, "advanced", 200, 300, 400, null, null, user2, null, null);
        farmerRepository.save(farmer1);
        farmerRepository.save(farmer2);
        farm1 = new Farm(null, "LIC-123", "pending", "Farm One", "Farm One Desc", "Small", "Vegetables", 4.5, "url", LocalDateTime.now(), farmer1, null, null, null);
        farm2 = new Farm(null, "LIC-456","pending", "Farm Two", "Farm Two Desc", "Large", "Fruits", 5.0, "url", LocalDateTime.now(), farmer2, null, null, null);
        farmRepository.save(farm1);
        farmRepository.save(farm2);
        field1 = new Field(null, "Growing", 50.5, LocalDateTime.now(), LocalDateTime.now(), farm1, null, null);
        field2 = new Field(null, "Harvested", 80.0, LocalDateTime.now(), LocalDateTime.now(), farm1, null, null);
        fieldRepository.save(field1);
        fieldRepository.save(field2);
    }

    @Test
    public void findFieldById() {
        Field field = fieldRepository.findFieldById(field1.getId());
        Assertions.assertThat(field.getId()).isEqualTo(field1.getId());
    }

    @Test
    public void findFieldByFarmIdAndFarm_Farmer_Id() {
        List<Field> fields = fieldRepository.findFieldByFarmIdAndFarm_Farmer_Id(farm1.getId(), farmer1.getId());

        Assertions.assertThat(fields).hasSize(2);
        Assertions.assertThat(fields).contains(field1, field2);
    }
}
