package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.VirtualFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VirtualFarmRepository extends JpaRepository<VirtualFarm,Integer> {

    List<VirtualFarm> findVirtualFarmByFarmer(Farmer farmer);

    VirtualFarm findVirtualFarmById(Integer virtualFarmId);
}
