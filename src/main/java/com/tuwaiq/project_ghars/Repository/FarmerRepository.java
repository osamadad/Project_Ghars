package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer,Integer> {

    Farmer findFarmerById(Integer id);
}
