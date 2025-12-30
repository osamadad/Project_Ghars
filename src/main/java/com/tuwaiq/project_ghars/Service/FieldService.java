package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.FieldDTOIn;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FarmerRepository farmerRepository;
    private final PlantTypeRepository plantTypeRepository;

    public void addField(Integer userId, FieldDTOIn fieldDTOIn) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }
        Farm farm = farmRepository.findFarmById(fieldDTOIn.getFarmId());
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        PlantType plantType=plantTypeRepository.findPlantTypeById(fieldDTOIn.getPlantTypeId());
        if (plantType==null){
            throw new ApiException("Plant not found");
        }
        if (!farm.getFarmer().getId().equals(farmer.getId())){
            throw new ApiException("You don't own this farm");
        }

        Field field = new Field();
        field.setExpectedYield(fieldDTOIn.getExpectedYield());
        field.setExpectedYieldTime(fieldDTOIn.getExpectedYieldTime());
        field.setCreatedAt(LocalDateTime.now());
        field.setStatus("Seedling");
        field.setFarm(farm);
        field.setPlantTypes(new HashSet<>());
        field.getPlantTypes().add(plantType);
        plantType.getFields().add(field);

        plantTypeRepository.save(plantType);
        fieldRepository.save(field);
    }

    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public List<Field> getMyFieldsByFarm(Integer userId, Integer farmId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }
        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        return fieldRepository.findFieldByFarmIdAndFarm_Farmer_Id(farmId,farmer.getId());
    }

    public void updateField(Integer userId, Integer fieldId, FieldDTOIn fieldDTOIn) {
        Field field = getFieldByFarmer(userId, fieldId);

        field.setExpectedYield(fieldDTOIn.getExpectedYield());
        field.setExpectedYieldTime(fieldDTOIn.getExpectedYieldTime());

        fieldRepository.save(field);
    }

    public void deleteField(Integer userId, Integer fieldId) {
        Field field = getFieldByFarmer(userId, fieldId);
        fieldRepository.delete(field);
    }

    private Field getFieldByFarmer(Integer userId, Integer fieldId) {
        Farmer farmer = farmerRepository.findFarmerById(userId);
        if (farmer == null) {
            throw new ApiException("Farmer not found");
        }
        Field field = fieldRepository.findFieldById(fieldId);
        if (field == null) {
            throw new ApiException("Field not found");
        }
        Farm farm=farmRepository.findFarmById(field.getFarm().getId());
        if (farm==null){
            throw new ApiException("Farm not found");
        }
        if (!farm.getFarmer().getId().equals(farmer.getId())){
            throw new ApiException("You dont own this farm");
        }
        return field;
    }
}
