package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {

    Level findLevelById(Integer id);

    Level findLevelByLevelNumber(Integer levelNumber);

    List<Level> findAllByOrderByLevelNumberAsc();
}
