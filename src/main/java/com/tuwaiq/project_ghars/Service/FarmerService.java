package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Config.Configuration;
import com.tuwaiq.project_ghars.DTOIn.FarmerDTOIn;
import com.tuwaiq.project_ghars.Model.*;
import com.tuwaiq.project_ghars.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final UserRepository userRepository;
    private final Configuration configuration;
    private final LevelRepository levelRepository;
    private final FarmerAchievementRepository farmerAchievementRepository;
    private final WhatsappService whatsappService;

    public void registerFarmer(FarmerDTOIn farmerDTOIn) {

        if (userRepository.findUserByUsername(farmerDTOIn.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }

        if (userRepository.findUserByEmail(farmerDTOIn.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        if (userRepository.findUserByPhoneNumber(farmerDTOIn.getPhoneNumber()) != null) {
            throw new ApiException("Phone number already exists");
        }


        User user = new User();
        user.setUsername(farmerDTOIn.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(farmerDTOIn.getPassword()));
        user.setName(farmerDTOIn.getName());
        user.setEmail(farmerDTOIn.getEmail());
        user.setPhoneNumber(farmerDTOIn.getPhoneNumber());
        user.setRole("FARMER");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Farmer farmer = new Farmer();
        farmer.setUser(user);

        farmer.setFarmerRank(farmerDTOIn.getFarmerRank());
        farmer.setFarmerExperience(farmerDTOIn.getFarmerExperience());

        farmerRepository.save(farmer);
    }

    public List<Farmer> getAllFarmer() {
        return farmerRepository.findAll();
    }

    public Farmer getMyFarmer(Integer userId) {

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

        return farmer;
    }

    public void updateMyFarmer(Integer userId, FarmerDTOIn farmerDTOIn) {

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

        User checkUsername = userRepository.findUserByUsername(farmerDTOIn.getUsername());
        if (checkUsername != null && !checkUsername.getId().equals(userId)) {
            throw new ApiException("Username already exists");
        }

        User checkEmail = userRepository.findUserByEmail(farmerDTOIn.getEmail());
        if (checkEmail != null && !checkEmail.getId().equals(userId)) {
            throw new ApiException("Email already exists");
        }

        User checkPhone = userRepository.findUserByPhoneNumber(farmerDTOIn.getPhoneNumber());
        if (checkPhone != null && !checkPhone.getId().equals(userId)) {
            throw new ApiException("Phone number already exists");
        }



        user.setUsername(farmerDTOIn.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(farmerDTOIn.getPassword()));
        user.setName(farmerDTOIn.getName());
        user.setEmail(farmerDTOIn.getEmail());
        user.setPhoneNumber(farmerDTOIn.getPhoneNumber());

        farmer.setFarmerRank(farmerDTOIn.getFarmerRank());
        farmer.setFarmerExperience(farmerDTOIn.getFarmerExperience());

        userRepository.save(user);
        farmerRepository.save(farmer);
    }

    public void deleteMyFarmer(Integer userId) {

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

        farmerRepository.delete(farmer);
        userRepository.delete(user);
    }

    public List<Farmer> getFarmersByCity(String city) {
        return farmerRepository.findFarmerByUser_Address_City(city);
    }


    public List<Farmer> getFarmersByRank(String farmerRank) {
        return farmerRepository.findFarmerByFarmerRank(farmerRank);
    }

    public List<Farmer> getFarmersByLevel(Integer minLevel, Integer maxLevel) {
        return farmerRepository.findFarmerByMinAndMaxLevels(minLevel,maxLevel);
    }

    public List<Farmer> getMostExperiencedFarmer() {
        return farmerRepository.findMostExperiencedFarmer();
    }

    public List<Farmer> getFarmersWhoPlantedPlant(String plantName) {
        return farmerRepository.getFarmerWhoPlantedThisPlant(plantName);
    }

    public List<Farmer> getFarmerWithTheMostYield() {
        return farmerRepository.findFarmerWithTheMostYield();
    }

    public void talkWithFarmers(Integer userId, Integer farmerId, String message){
        Farmer farmer=farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("You are not a farmer");
        }
        Farmer receverFarmer=farmerRepository.findFarmerById(farmerId);
        if (receverFarmer==null){
            throw new ApiException("Farmer not found");
        }

        whatsappService.sendWhatsAppMessage(receverFarmer.getUser().getPhoneNumber(),message);
    }

    public void talkWithFarmersWhoPlantedAPlant(Integer userId, Integer farmerId, String plantName){
        Farmer farmer=farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("You are not a farmer");
        }
        Farmer receverFarmer=farmerRepository.findFarmerById(farmerId);
        if (receverFarmer==null){
            throw new ApiException("Farmer not found");
        }
        List<Farmer> farmers=farmerRepository.getFarmerWhoPlantedThisPlant(plantName);

        if (farmers.isEmpty()){
            throw new ApiException("No farmer planted this plant");
        }

        String prompt = """
                مرحبًا! 👋
                لاحظت أنك زرعت %s من قبل. أنا أخطط لزراعته بنفسي،
                ويسعدني أن أسمع عن تجربتك.

                كيف كانت تجربتك معه؟
                هل لديك نصائح أو أشياء تتمنى لو كنت تعرفها في وقتٍ أبكر؟

                شكرًا مقدمًا!
                """.formatted(plantName);

        whatsappService.sendWhatsAppMessage(farmers.get(0).getUser().getPhoneNumber(),prompt);
    }
}
