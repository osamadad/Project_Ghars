package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Achievement;
import com.tuwaiq.project_ghars.Model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<AchievementRepository,Integer> {
    List<Achievement> findAchievementByFarmer(Farmer farmer);
}
