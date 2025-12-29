package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Model.VirtualFarm;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import com.tuwaiq.project_ghars.Repository.VirtualFarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VirtualFarmService {

    private final VirtualFarmRepository virtualFarmRepository;
    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;

    public void addVirtualFarm(Integer userId, VirtualFarm virtualFarm) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        virtualFarm.setId(null);
        //virtualFarm.setPlots();
        virtualFarm.setFarmer(farmer);

        virtualFarmRepository.save(virtualFarm);
    }

    public List<VirtualFarm> getMyVirtualFarms(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }

        return virtualFarmRepository.findVirtualFarmByFarmer(farmer);
    }

    public VirtualFarm getMyVirtualFarmById(Integer userId, Integer virtualFarmId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        VirtualFarm virtualFarm = virtualFarmRepository.findVirtualFarmById(virtualFarmId);
        if (virtualFarm == null) {
            throw new ApiException("Virtual farm not found");
        }

        if (!virtualFarm.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to access this virtual farm");
        }

        return virtualFarm;
    }

    public void updateVirtualFarm(Integer userId, Integer virtualFarmId, VirtualFarm virtualFarmRequest) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        VirtualFarm virtualFarm = virtualFarmRepository.findVirtualFarmById(virtualFarmId);
        if (virtualFarm == null) {
            throw new ApiException("Virtual farm not found");
        }

        if (!virtualFarm.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to update this virtual farm");
        }

        virtualFarm.setName(virtualFarmRequest.getName());

        virtualFarmRepository.save(virtualFarm);
    }

    public void deleteVirtualFarm(Integer userId, Integer virtualFarmId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        VirtualFarm virtualFarm = virtualFarmRepository.findVirtualFarmById(virtualFarmId);
        if (virtualFarm == null) {
            throw new ApiException("Virtual farm not found");
        }

        if (!virtualFarm.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to delete this virtual farm");
        }

        virtualFarmRepository.delete(virtualFarm);
    }
}
