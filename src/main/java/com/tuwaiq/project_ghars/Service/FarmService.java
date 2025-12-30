package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.FarmDTOIn;
import com.tuwaiq.project_ghars.DTOIn.LicenseDTOIn;
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
    private final UserRepository userRepository ;
    private final EmailService emailService ;

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

    public List<Farm> getMyFarm(Integer userId) {
        Farmer farmer=farmerRepository.findFarmerById(userId);
        if (farmer==null){
            throw new ApiException("Farmer not found");
        }
        List<Farm> farm = farmRepository.findFarmByFarmer_Id(farmer.getId());
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
        if (!farmDTOIn.getSize().matches("Small|Medium|Large")){
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


    public void addLicense(Integer farmId , LicenseDTOIn licenseDTOIn){
        Farm LFarm = farmRepository.findFarmById(farmId);

        if(LFarm==null) throw new ApiException("Farm not found");

        LFarm.setLicense(licenseDTOIn.getLicense());
        LFarm.setLicenseStaus("PENDING");
        farmRepository.save(LFarm);

    }


    public void acceptLicense(Integer userId, Integer farmId) {

        User admin = userRepository.findUserById(userId);
        if (admin == null)
            throw new ApiException("User not found");

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null)
            throw new ApiException("Farm not found");

        if (farm.getLicense() == null || farm.getLicense().isEmpty())
            throw new ApiException("Farm has no license submitted");

        farm.setLicenseStaus("ACCEPTED");
        farmRepository.save(farm);

        String email = farm.getFarmer().getUser().getEmail();

        String subject = "تم قبول رخصة المزرعة 🌱";
        String body =
                "مرحبًا،\n\n" +
                        "تم قبول رخصة مزرعتك: " + farm.getName() + "\n\n" +
                        "يمكنك الآن الاستفادة من جميع خدمات منصة غرس.\n\n" +
                        "🌱 فريق غرس";

        emailService.sendEmail(email, subject, body);
    }




    public void rejectLicense(Integer userId, Integer farmId) {

        User admin = userRepository.findUserById(userId);
        if (admin == null)
            throw new ApiException("User not found");

        Farm farm = farmRepository.findFarmById(farmId);
        if (farm == null)
            throw new ApiException("Farm not found");

        farm.setLicenseStaus("REJECTED");
        farmRepository.save(farm);

        String email = farm.getFarmer().getUser().getEmail();

        String subject = "تم رفض رخصة المزرعة ❌";
        String body =
                "مرحبًا،\n\n" +
                        "نأسف لإبلاغك بأنه تم رفض رخصة مزرعتك: " + farm.getName() + "\n\n" +
                        "يمكنك تعديل البيانات وإعادة التقديم في أي وقت.\n\n" +
                        "🌱 فريق غرس";

        emailService.sendEmail(email, subject, body);
    }


}
