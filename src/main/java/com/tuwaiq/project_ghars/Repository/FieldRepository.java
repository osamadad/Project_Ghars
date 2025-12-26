package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FieldRepository extends JpaRepository<Field,Integer> {

    Field findFieldById(Integer id);

    List<Field> findFieldByFarmIdAndFarm_Farmer_Id(Integer farmId, Integer farmFarmerId);
}
