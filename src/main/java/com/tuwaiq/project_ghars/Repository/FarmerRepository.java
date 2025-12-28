package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer> {

    Farmer findFarmerById(Integer id);

    List<Farmer> findFarmerByUser_Address_City(String userAddressCity);

    List<Farmer> findFarmerByFarmerRank(String farmerRank);

    List<Farmer> findFarmerByFarmerExperience(Integer farmerExperience);

    List<Farmer> findFarmerByLevel_Id(Integer levelId);

    @Query("select farmer from Farmer farmer join farmer.farms farm join farm.fields field join field.plantTypes plant where plant.commonName=?1 and field.status='Harvested'")
    List<Farmer> getFarmerWhoPlantedThisPlant(String plantName);
}
