package com.tuwaiq.project_ghars.Repository;

import com.tuwaiq.project_ghars.Model.VirtualFarm;
import com.tuwaiq.project_ghars.Model.VirtualPlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VirtualPlotRepository extends JpaRepository<VirtualPlot,Integer> {
    List<VirtualPlot> findVirtualPlotByVirtualFarm(VirtualFarm virtualFarm);

    VirtualPlot findVirtualPlotById(Integer virtualPlotId);
}
