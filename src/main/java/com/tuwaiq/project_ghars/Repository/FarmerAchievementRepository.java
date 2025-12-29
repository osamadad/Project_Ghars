package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.FarmerAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerAchievementRepository extends JpaRepository<FarmerAchievement, Integer> {

    FarmerAchievement findFarmerAchievementById(Integer id);

    List<FarmerAchievement> findFarmerAchievementByFarmer_Id(Integer farmerId);
}
