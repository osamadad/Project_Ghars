package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VirtualPlotService {

    private final VirtualPlotRepository virtualPlotRepository;
    private final VirtualFarmRepository virtualFarmRepository;
    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final LevelRepository levelRepository;
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

        String urlWebhook="https://ososdad.app.n8n.cloud/webhook-test/4bf991e7-e740-4da3-9ded-30c0e32ca68b";
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder
                .fromUriString(urlWebhook)
                .queryParam("id", plantId)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        restTemplate.postForEntity(url, requestEntity, String.class);

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

    public void addWater(Integer plotId){
        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Plot not found");
        }
        if (virtualPlot.getPlantType()==null){
            throw new ApiException("There is no plant in this plot");
        }
        virtualPlot.setWaterMeter(virtualPlot.getWaterMeter()+15);

        virtualPlotRepository.save(virtualPlot);
    }

    public void addSun(Integer plotId){
        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Plot not found");
        }
        if (virtualPlot.getPlantType()==null){
            throw new ApiException("There is no plant in this plot");
        }
        virtualPlot.setSunMeter(virtualPlot.getSunMeter()+15);

        virtualPlotRepository.save(virtualPlot);
    }

    public void decreaseWater(Integer plotId){
        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Plot not found");
        }
        if (virtualPlot.getPlantType()==null){
            throw new ApiException("There is no plant in this plot");
        }
        virtualPlot.setWaterMeter(virtualPlot.getWaterMeter()-10);

        virtualPlotRepository.save(virtualPlot);
    }

    public void decreaseSun(Integer plotId){
        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Plot not found");
        }
        if (virtualPlot.getPlantType()==null){
            throw new ApiException("There is no plant in this plot");
        }
        virtualPlot.setSunMeter(virtualPlot.getSunMeter()-10);

        virtualPlotRepository.save(virtualPlot);
    }

    public void checkPlant(Integer plotId){
        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Plot not found");
        }
        if (virtualPlot.getPlantType()==null){
            throw new ApiException("There is no plant in this plot");
        }
        if (virtualPlot.getStatus().equalsIgnoreCase("dead")){
            throw new ApiException("The plant is dead");
        }
        Integer health=virtualPlot.getHealth();
        Integer progress=virtualPlot.getProgress();
        Integer sunMeter= virtualPlot.getSunMeter();
        Integer waterMeter= virtualPlot.getWaterMeter();
        String sunNeeds=virtualPlot.getPlantType().getSunNeeds();
        String waterNeeds=virtualPlot.getPlantType().getWaterNeeds();

        Integer waterThreshold;
        Integer sunThreshold;
        if (waterNeeds.equalsIgnoreCase("low")){
            waterThreshold=70;
        }
        else if (waterNeeds.equalsIgnoreCase("medium")){
            waterThreshold=100;
        }
        else {
            waterThreshold=130;
        }
        if (waterMeter>waterThreshold){
            health-=10;
        }
        if (waterMeter<50){
            health-=10;
        }

        if (sunNeeds.equalsIgnoreCase("low")){
            sunThreshold=70;
        }
        else if (sunNeeds.equalsIgnoreCase("medium")){
            sunThreshold=100;
        }
        else {
            sunThreshold=130;
        }
        if (sunMeter>sunThreshold){
            health-=10;
        }
        if (sunMeter<50){
            health-=10;
        }

        virtualPlot.setHealth(health);
        if (health<=0){
            virtualPlot.setStatus("dead");
        }else {
            progress+=10;
            if (progress>=100){
                virtualPlot.setStatus("ready");
            }
            virtualPlot.setProgress(progress);
        }

        virtualPlotRepository.save(virtualPlot);
    }

    public void harvestPlant(Integer plotId){
        VirtualPlot virtualPlot=virtualPlotRepository.findVirtualPlotById(plotId);
        if (virtualPlot==null){
            throw new ApiException("Plot not found");
        }
        if (virtualPlot.getPlantType()==null){
            throw new ApiException("There is no plant in this plot");
        }
        if (virtualPlot.getStatus().equalsIgnoreCase("dead")){
            throw new ApiException("The plant is dead");
        }
        if (virtualPlot.getStatus().equalsIgnoreCase("ready")){
            Farmer farmer=farmerRepository.findFarmerById(virtualPlot.getVirtualFarm().getFarmer().getId());
            if (farmer==null){
                throw new ApiException("Farmer not found");
            }
            Integer finalYield= (int) (virtualPlot.getExpectedYield()+(virtualPlot.getHealth()*0.5));
            Integer experienceGained= (int) (virtualPlot.getExperienceGiven()+(virtualPlot.getHealth()*0.5));
            farmer.setTotalYield(farmer.getTotalYield()+finalYield);
            farmer.setSeasonalYield(farmer.getSeasonalYield()+finalYield);
            farmer.setFarmerExperience(farmer.getFarmerExperience()+experienceGained);
            Level level=levelRepository.findLevelById(farmer.getLevel().getId()+1);
            if (level==null){
                throw new ApiException("You have reached the max level");
            }
            if (farmer.getFarmerExperience()>=level.getRequiredExp()){
                farmer.setLevel(level);
            }
        }

        virtualPlotRepository.save(virtualPlot);
    }

}
