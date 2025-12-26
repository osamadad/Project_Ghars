package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.YieldDTOIn;
import com.tuwaiq.project_ghars.Model.Field;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Model.Yield;
import com.tuwaiq.project_ghars.Repository.FieldRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import com.tuwaiq.project_ghars.Repository.YieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YieldService {

    private final YieldRepository yieldRepository;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    public void addYield(Integer userId, YieldDTOIn yieldDTOIn) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Field field = fieldRepository.findFieldById(yieldDTOIn.getFieldId());
        if (field == null) {
            throw new ApiException("Field not found");
        }

        /*
         * check user owns the farm of this field
         */

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

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Yield yield = yieldRepository.findYieldById(yieldId);
        if (yield == null) {
            throw new ApiException("Yield not found");
        }

        /*
         * check user owns the farm of this yield
         */

        yield.setHarvestDate(yieldDTOIn.getHarvestDate());
        yield.setHarvestYield(yieldDTOIn.getHarvestYield());
        yield.setQuality(yieldDTOIn.getQuality());

        yieldRepository.save(yield);
    }

    public void deleteYield(Integer userId, Integer yieldId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        Yield yield = yieldRepository.findYieldById(yieldId);
        if (yield == null) {
            throw new ApiException("Yield not found");
        }

        yieldRepository.delete(yield);
    }
}
