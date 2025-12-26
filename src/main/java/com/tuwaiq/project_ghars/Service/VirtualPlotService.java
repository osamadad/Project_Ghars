package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Model.VirtualFarm;
import com.tuwaiq.project_ghars.Model.VirtualPlot;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import com.tuwaiq.project_ghars.Repository.VirtualFarmRepository;
import com.tuwaiq.project_ghars.Repository.VirtualPlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VirtualPlotService {

    private final VirtualPlotRepository virtualPlotRepository;
    private final VirtualFarmRepository virtualFarmRepository;
    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    public void addVirtualPlot(Integer userId, Integer virtualFarmId, VirtualPlot virtualPlot) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        VirtualFarm virtualFarm = virtualFarmRepository.findVirtualFarmById(virtualFarmId);
        if (virtualFarm == null) {
            throw new ApiException("Virtual farm not found");
        }

        if (!virtualFarm.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to add plot to this virtual farm");
        }

        virtualPlot.setId(null);
        virtualPlot.setVirtualFarm(virtualFarm);

        virtualPlotRepository.save(virtualPlot);
    }

    public List<VirtualPlot> getMyVirtualPlots(Integer userId, Integer virtualFarmId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        VirtualFarm virtualFarm = virtualFarmRepository.findVirtualFarmById(virtualFarmId);
        if (virtualFarm == null) {
            throw new ApiException("Virtual farm not found");
        }

        if (!virtualFarm.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to access this virtual farm");
        }

        return virtualPlotRepository.findVirtualPlotByVirtualFarm(virtualFarm);
    }

    public VirtualPlot getMyVirtualPlotById(Integer userId, Integer virtualPlotId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        VirtualPlot virtualPlot = virtualPlotRepository.findVirtualPlotById(virtualPlotId);
        if (virtualPlot == null) {
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to access this virtual plot");
        }

        return virtualPlot;
    }

    public void updateVirtualPlot(Integer userId, Integer virtualPlotId, VirtualPlot virtualPlotRequest) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        VirtualPlot virtualPlot = virtualPlotRepository.findVirtualPlotById(virtualPlotId);
        if (virtualPlot == null) {
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to update this virtual plot");
        }

        virtualPlot.setName(virtualPlotRequest.getName());
        virtualPlot.setStatus(virtualPlotRequest.getStatus());
        virtualPlot.setSunMeter(virtualPlotRequest.getSunMeter());
        virtualPlot.setWaterMeter(virtualPlotRequest.getWaterMeter());

        virtualPlotRepository.save(virtualPlot);
    }

    public void deleteVirtualPlot(Integer userId, Integer virtualPlotId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("FARMER")) {
            throw new ApiException("Access denied");
        }

        VirtualPlot virtualPlot = virtualPlotRepository.findVirtualPlotById(virtualPlotId);
        if (virtualPlot == null) {
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to delete this virtual plot");
        }

        virtualPlotRepository.delete(virtualPlot);
    }
}
