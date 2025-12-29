package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Integer> {

    Achievement findAchievementById(Integer id);
}
