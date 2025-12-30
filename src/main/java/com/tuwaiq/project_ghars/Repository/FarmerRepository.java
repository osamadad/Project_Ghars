package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Integer> {

    Farmer findFarmerById(Integer id);

    List<Farmer> findFarmerByUser_Address_City(String userAddressCity);

    List<Farmer> findFarmerByFarmerRank(String farmerRank);

    @Query("select farmer from Farmer farmer where farmer.level.levelNumber>=?1 and farmer.level.levelNumber<=2")
    List<Farmer> findFarmerByMinAndMaxLevels(Integer minLevel, Integer maxLevel);

    @Query("select farmer from Farmer farmer order by farmer.level.levelNumber")
    List<Farmer> findMostExperiencedFarmer();

    @Query("select farmer from Farmer farmer join farmer.farms farm join farm.fields field join field.plantTypes plant where plant.commonName=?1 and field.status='Harvested' order by farmer.totalYield asc ")
    List<Farmer> getFarmerWhoPlantedThisPlant(String plantName);

    @Query("select farmer from Farmer farmer order by farmer.totalYield")
    List<Farmer> findFarmerWithTheMostYield();

    @Query("select farmer from Farmer farmer order by farmer.totalYield")
    List<Farmer> findFarmerWithTheMostSeasonalYield();
}
