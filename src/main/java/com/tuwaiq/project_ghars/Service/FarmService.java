package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.FarmDTOIn;
import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.FarmRepository;
import com.tuwaiq.project_ghars.Repository.FarmerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;

    public void addFarm(Integer userId, FarmDTOIn farmDTOIn) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("farmer not found");
        }
        if (!farmer.getId().equals(farmDTOIn.getFarmerId())) {
            throw new ApiException("farmer id mismatch, use your own id");
        }

        Farm farm = new Farm();
        farm.setLicense(farmDTOIn.getLicense());
        farm.setName(farmDTOIn.getName());
        farm.setDescription(farmDTOIn.getDescription());
        farm.setSize(farmDTOIn.getSize());
        farm.setSpeciality(farmDTOIn.getSpeciality());
        farm.setPhotoUrl(farmDTOIn.getPhotoUrl());
        farm.setFarmer(farmer);
        farm.setRating(0.0);
        farm.setCreatedAt(LocalDateTime.now());

        farmRepository.save(farm);
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public List<Farm> getMyFarm(Integer userId, Integer farmId) {
        Farmer farmer=farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("Farmer not found");
        }
        List<Farm> farm = farmRepository.findFarmByIdAndFarmer_Id(farmId,farmer.getId());
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        return farm;
    }

    public void updateFarm(Integer userId, Integer farmId, FarmDTOIn farmDTOIn) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("User not found");
        }

        if (!farmer.getId().equals(farmDTOIn.getFarmerId())) {
            throw new ApiException("User id mismatch, use your own id");
        }

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        if (!farm.getFarmer().getId().equals(farmer.getId())){
            throw new ApiException("You don't own this farm");
        }

        farm.setLicense(farmDTOIn.getLicense());
        farm.setName(farmDTOIn.getName());
        farm.setDescription(farmDTOIn.getDescription());
        if (!farmDTOIn.getSize().matches("Small| Medium | Large")){
            throw new ApiException("Sorry, the farm size must be Small, Medium, or large, please try again");
        }
        farm.setSize(farmDTOIn.getSize());
        farm.setSpeciality(farmDTOIn.getSpeciality());
        farm.setPhotoUrl(farmDTOIn.getPhotoUrl());

        farmRepository.save(farm);
    }

    public void deleteFarm(Integer userId, Integer farmId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("User not found");
        }
        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        if (!farm.getFarmer().getId().equals(farmer.getId())){
            throw new ApiException("You don't own this farm");
        }

        farmRepository.delete(farm);
    }
}
