package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.FieldDTOIn;
import com.tuwaiq.project_ghars.Model.Farm;
import com.tuwaiq.project_ghars.Model.Field;
import com.tuwaiq.project_ghars.Model.PlantType;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.FarmRepository;
import com.tuwaiq.project_ghars.Repository.FieldRepository;
import com.tuwaiq.project_ghars.Repository.PlantTypeRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;
    private final PlantTypeRepository plantTypeRepository;

    public void addField(Integer userId, FieldDTOIn fieldDTOIn) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Farm farm = farmRepository.findFarmById(fieldDTOIn.getFarmId());
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        PlantType plantType=plantTypeRepository.findPlantTypeById(fieldDTOIn.getPlantTypeId());
        if (plantType==null){
            throw new ApiException("Plant not found");
        }

        /*
         * check user owns the farm
         */

        Field field = new Field();
        field.setExpectedYield(fieldDTOIn.getExpectedYield());
        field.setExpectedYieldTime(fieldDTOIn.getExpectedYieldTime());
        field.setCreatedAt(LocalDateTime.now());
        field.setStatus("Seedling");
        field.setFarm(farm);
        field.setPlantType(plantType);

        fieldRepository.save(field);
    }

    public List<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    public List<Field> getMyFieldsByFarm(Integer userId, Integer farmId) {
        // check user/farmer
        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null) {
            throw new ApiException("Farm not found");
        }
        return fieldRepository.findFieldByFarmIdAndFarm_Farmer_Id(userId,farmId);
    }

    public void updateField(Integer userId, Integer fieldId, FieldDTOIn fieldDTOIn) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Field field = fieldRepository.findFieldById(fieldId);
        if (field == null) {
            throw new ApiException("Field not found");
        }

        /*
         * check user owns the farm of this field
         */

        field.setExpectedYield(fieldDTOIn.getExpectedYield());
        field.setExpectedYieldTime(fieldDTOIn.getExpectedYieldTime());

        fieldRepository.save(field);
    }

    public void deleteField(Integer userId, Integer fieldId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }
        /*
         * check user owns the farm of this field
         */
        Field field = fieldRepository.findFieldById(fieldId);
        if (field == null) {
            throw new ApiException("Field not found");
        }

        fieldRepository.delete(field);
    }
}
