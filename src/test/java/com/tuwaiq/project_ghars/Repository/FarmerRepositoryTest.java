package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FarmerRepositoryTest {

    @Autowired
    FarmerRepository farmerRepository;
    @Autowired
    UserRepository userRepository;

    User user1,user2,user3;
    Farmer farmer1,farmer2,farmer3;
    Farmer farmer;
    List<Farmer> farmers;

    @BeforeEach
    void setUp(){
        user1=new User(null,"osama","osama@gmail.com","osamadad","osamadad768","0500000000","FARMER",LocalDateTime.now(),null,null,null,null);
        user1=new User(null,"mohammed","mohammed@gmail.com","mohammeddad","mohammeddad768","0550000000","FARMER",LocalDateTime.now(),null,null,null,null);
        user1=new User(null,"ahmed","ahmed@gmail.com","ahmeddad","ahmeddad768","0555000000","FARMER",LocalDateTime.now(),null,null,null,null);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        farmer1=new Farmer(null,"beginner","10",user1,null,null,null);
        farmer2=new Farmer(null,"advanced","50",user2,null,null,null);
        farmer3=new Farmer(null,"beginner","15",user3,null,null,null);
        farmerRepository.save(farmer1);
        farmerRepository.save(farmer2);
        farmerRepository.save(farmer3);
    }

    @Test
    public void findFarmerById(){
        farmer=farmerRepository.findFarmerById(1);
        Assertions.assertThat(farmer.getId().equals(farmer1.getId()));
    }
}
