package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.FarmDTOIn;
import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Farmer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.FarmRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    // all need checking
    public void addFarm(Integer userId, FarmDTOIn farmDTOIn) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        /*
         * check user is the farmer
         * */
        if (!user.getId().equals(farmDTOIn.getFarmerId())) {
            throw new ApiException("User id mismatch, use your own id");
        }

        Farm farm = new Farm();
        farm.setLicense(farmDTOIn.getLicense());
        farm.setName(farmDTOIn.getName());
        farm.setDescription(farmDTOIn.getDescription());
        farm.setSize(farmDTOIn.getSize());
        farm.setSpeciality(farmDTOIn.getSpeciality());
        farm.setPhotoUrl(farmDTOIn.getPhotoUrl());
        //farm.setFarmer();
        farm.setRating(0.0);
        farm.setCreatedAt(LocalDateTime.now());

        farmRepository.save(farm);
    }

    public List<Farm> getAllFarms() {
        return farmRepository.findAll();
    }

    public Farm getMyFarm(Integer userId, Integer farmId) {

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        return farm;
    }

    public void updateFarm(Integer userId, Integer farmId, FarmDTOIn farmDTOIn) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getId().equals(farmDTOIn.getFarmerId())) {
            throw new ApiException("User id mismatch, use your own id");
        }

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }

        farm.setLicense(farmDTOIn.getLicense());
        farm.setName(farmDTOIn.getName());
        farm.setDescription(farmDTOIn.getDescription());
        farm.setSize(farmDTOIn.getSize());
        farm.setSpeciality(farmDTOIn.getSpeciality());
        farm.setPhotoUrl(farmDTOIn.getPhotoUrl());

        farmRepository.save(farm);
    }

    public void deleteFarm(Integer userId, Integer farmId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        /*
        * check user is the farmer
        * */
        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }

        farmRepository.delete(farm);
    }
}
