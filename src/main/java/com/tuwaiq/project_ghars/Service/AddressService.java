package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.DTOIn.AddressDTOIn;
import com.tuwaiq.project_ghars.Model.Address;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.AddressRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public void addAddress(Integer userId, AddressDTOIn addressDTOIn) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
//        if (!user.getId().equals(addressDTOIn.getUserId())){
//            throw new ApiException("User id mismatch use your own id in address");
//        }
        Address address=new Address(null, addressDTOIn.getCountry(), addressDTOIn.getCity(), addressDTOIn.getStreet(), addressDTOIn.getBuildingNumber(), addressDTOIn.getPostalNumber(),user);
        addressRepository.save(address);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getMyAddress(Integer userId) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        return addressRepository.findAddressesById(userId);
    }

    public void updateAddress(Integer userId, AddressDTOIn addressDTOIn) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        if (!user.getId().equals(addressDTOIn.getUserId())){
            throw new ApiException("User id mismatch use your own id in address");
        }
        Address address = addressRepository.findAddressesById(userId);
        if (address==null){
            throw new ApiException("Address not found");
        }

        address.setCountry(addressDTOIn.getCountry());
        address.setCity(addressDTOIn.getCity());
        address.setStreet(addressDTOIn.getStreet());
        address.setBuildingNumber(addressDTOIn.getBuildingNumber());
        address.setPostalNumber(addressDTOIn.getPostalNumber());

        addressRepository.save(address);
    }

    public void deleteAddress(Integer userId) {
        User user=userRepository.findUserById(userId);
        if (user==null){
            throw new ApiException("User not found");
        }
        Address address = addressRepository.findAddressesById(userId);
        if (address==null){
            throw new ApiException("Address not found");
        }
        addressRepository.delete(address);
    }
}
