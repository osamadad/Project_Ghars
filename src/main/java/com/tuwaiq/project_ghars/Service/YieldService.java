package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.YieldDTOIn;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YieldService {

    private final YieldRepository yieldRepository;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;
    private final FarmerRepository farmerRepository;
    private final FarmRepository farmRepository;

    public void addYield(Integer userId, YieldDTOIn yieldDTOIn) {
        Field field = getFieldByFarmer(userId,yieldDTOIn.getFieldId());

        Yield yield = new Yield();
        yield.setHarvestDate(yieldDTOIn.getHarvestDate());
        yield.setHarvestYield(yieldDTOIn.getHarvestYield());
        yield.setQuality(yieldDTOIn.getQuality());
        yield.setField(field);

        yieldRepository.save(yield);
    }

    public List<Yield> getAllYields() {
        return yieldRepository.findAll();
    }

    public List<Yield> getMyYieldsByField(Integer userId, Integer fieldId) {

        Field field = fieldRepository.findFieldById(fieldId);
        if (field == null) {
            throw new ApiException("Field not found");
        }

        return yieldRepository.findYieldByField_IdAndField_Farm_Farmer_Id(fieldId,userId);
    }

    public void updateYield(Integer userId, Integer yieldId, YieldDTOIn yieldDTOIn) {

        Yield yield = yieldRepository.findYieldById(yieldId);
        if (yield == null) {
            throw new ApiException("Yield not found");
        }
        getFieldByFarmer(userId,yield.getField().getFarm().getId());

        yield.setHarvestDate(yieldDTOIn.getHarvestDate());
        yield.setHarvestYield(yieldDTOIn.getHarvestYield());
        yield.setQuality(yieldDTOIn.getQuality());

        yieldRepository.save(yield);
    }

    public void deleteYield(Integer userId, Integer yieldId) {
        Yield yield = yieldRepository.findYieldById(yieldId);
        if (yield == null) {
            throw new ApiException("Yield not found");
        }
        getFieldByFarmer(userId,yield.getField().getFarm().getId());

        yieldRepository.delete(yield);
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
