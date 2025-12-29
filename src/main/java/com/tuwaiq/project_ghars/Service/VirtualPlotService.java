package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
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
    private final PlantTypeRepository plantTypeRepository;
    private final AIService aiService;

    public void addVirtualPlot(Integer userId, Integer virtualFarmId, String plotType) {

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

        VirtualFarm virtualFarm = virtualFarmRepository.findVirtualFarmById(virtualFarmId);
        if (virtualFarm == null) {
            throw new ApiException("Virtual farm not found");
        }

        if (!virtualFarm.getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to add plot to this virtual farm");
        }

        VirtualPlot virtualPlot=new VirtualPlot();
        if (!plotType.matches("indoor|outdoor")){
            throw new ApiException("The plot type must be indoor or outdoor");
        }
        virtualPlot.setPlotType(plotType);
        virtualPlot.setVirtualFarm(virtualFarm);

        virtualPlotRepository.save(virtualPlot);
    }

    public List<VirtualPlot> getMyVirtualPlots(Integer userId, Integer virtualFarmId) {

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

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        VirtualPlot virtualPlot = virtualPlotRepository.findVirtualPlotById(virtualPlotId);
        if (virtualPlot == null) {
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to access this virtual plot");
        }

        return virtualPlot;
    }


    public void deleteVirtualPlot(Integer userId, Integer virtualPlotId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

//        if (!user.getRole().equals("FARMER")) {
//            throw new ApiException("Access denied");
//        }

        VirtualPlot virtualPlot = virtualPlotRepository.findVirtualPlotById(virtualPlotId);
        if (virtualPlot == null) {
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(userId)) {
            throw new ApiException("You are not allowed to delete this virtual plot");
        }

        virtualPlotRepository.delete(virtualPlot);
    }

    public void assignAPlantToVirtualPlot(Integer userId, Integer plotId, Integer plantId){
        Farmer farmer=farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("Farmer not found");
        }

        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(farmer.getId())){
            throw new ApiException("You don't own the virtual farm that this plot belong to");
        }

        PlantType plantType=plantTypeRepository.findPlantTypeById(plantId);
        if (plantType==null){
            throw new ApiException("Plant doesn't exist");
        }

        VirtualPlot virtualPlot1=aiService.convertPlantToVirtualPlot(plantType);

        virtualPlot.setProgress(virtualPlot1.getProgress());
        virtualPlot.setHealth(virtualPlot1.getHealth());
        virtualPlot.setStatus(virtualPlot1.getStatus());
        virtualPlot.setExpectedYield(virtualPlot1.getExpectedYield());
        virtualPlot.setActualYield(virtualPlot1.getActualYield());
        virtualPlot.setExperienceGiven(virtualPlot1.getExperienceGiven());
        virtualPlot.setKnowledgeMeter(virtualPlot1.getKnowledgeMeter());
        virtualPlot.setWaterMeter(virtualPlot1.getWaterMeter());
        virtualPlot.setSunMeter(virtualPlot1.getSunMeter());
        virtualPlot.setVerificationPic(virtualPlot1.getVerificationPic());
        virtualPlot.setPlantedAt(virtualPlot1.getPlantedAt());

        virtualPlot.setPlantType(plantType);

        virtualPlotRepository.save(virtualPlot);

    }

    public void upRootPlantInVirtualPlot(Integer userId, Integer plotId){
        Farmer farmer=farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("Farmer not found");
        }

        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Virtual plot not found");
        }

        if (!virtualPlot.getVirtualFarm().getFarmer().getId().equals(farmer.getId())){
            throw new ApiException("You don't own the virtual farm that this plot belong to");
        }

        virtualPlot.setProgress(null);
        virtualPlot.setHealth(null);
        virtualPlot.setStatus(null);
        virtualPlot.setExpectedYield(null);
        virtualPlot.setActualYield(null);
        virtualPlot.setExperienceGiven(null);
        virtualPlot.setKnowledgeMeter(null);
        virtualPlot.setWaterMeter(null);
        virtualPlot.setSunMeter(null);
        virtualPlot.setVerificationPic(null);
        virtualPlot.setPlantedAt(null);

        virtualPlot.setPlantType(null);

        virtualPlotRepository.save(virtualPlot);
    }
}
