package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Address;
import com.tuwaiq.project_ghars.Model.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm,Integer> {

    Farm findFarmById(Integer id);

    List<Farm> findFarmByIdAndFarmer_Id(Integer id, Integer farmerId);
}
