package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Yield;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YieldRepository extends JpaRepository<Yield,Integer> {

    Yield findYieldById(Integer id);

    List<Yield> findYieldByField_IdAndField_Farm_Farmer_Id(Integer fieldId, Integer fieldFarmFarmerId);
}
