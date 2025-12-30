package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.DTOIn.AddressDTOIn;
import com.tuwaiq.project_ghars.Model.Address;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.AddressRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;

    User user1,user2,user3;
    Address address1,address2;
    AddressDTOIn addressDTOIn;
    List<Address> addresses=new ArrayList<>();

    @BeforeEach
    public void setUp(){
        user1 = new User(1, "osama", "osama@gmail.com", "osama", "osamadad768", "0500000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        user2 = new User(2, "mohammed", "mohammed@gmail.com", "mohammed", "mohammeddad768", "0550000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        user3 = new User(3, "ahmed", "ahmed@gmail.com", "ahmed", "ahmeddad768", "0555000000", "FARMER", LocalDateTime.now(), null, null, null, null, null);
        address1 = new Address(null, "Saudi Arabia", "Riyadh", "king street", "1234", "0551394555", user1);
        address2 = new Address(null, "Saudi Arabia", "Jeddah", "Prince street", "9876", "0551394555", user2);
        addresses.add(address1);
        addresses.add(address2);
    }

    @Test
    public void addAddress(){
        when(userRepository.findUserById(user3.getId())).thenReturn(user3);
        addressDTOIn=new AddressDTOIn("Saudi Arabia", "Riyadh", "queen street", "1234", "0551394555",user3.getId());
        addressService.addAddress(user3.getId(), addressDTOIn);
        verify(userRepository,times(1)).findUserById(user3.getId());
        verify(addressRepository,times(1)).save(any(Address.class));
    }

    @Test
    public void getAllAddresses(){
        when(addressRepository.findAll()).thenReturn(addresses);
        List<Address> addressList=addressService.getAllAddresses();
        Assertions.assertEquals(addresses,addressList);
        verify(addressRepository,times(1)).findAll();
    }

    @Test
    public void getMyAddress(){
        when(userRepository.findUserById(user1.getId())).thenReturn(user1);
        when(addressRepository.findAddressesById(user1.getId())).thenReturn(address1);
        Address address=addressService.getMyAddress(user1.getId());
        Assertions.assertEquals(address1,address);
        verify(userRepository,times(1)).findUserById(user1.getId());
        verify(addressRepository,times(1)).findAddressesById(user1.getId());
    }

    @Test
    public void updateAddress(){
        when(userRepository.findUserById(user2.getId())).thenReturn(user2);
        when(addressRepository.findAddressesById(user2.getId())).thenReturn(address2);
        addressDTOIn=new AddressDTOIn("Saudi Arabia", "Jeddah", "Prince street", "9876", "0551394555",user2.getId());
        addressService.updateAddress(user2.getId(), addressDTOIn);
        verify(userRepository,times(1)).findUserById(user2.getId());
        verify(addressRepository,times(1)).findAddressesById(user2.getId());
        verify(addressRepository,times(1)).save(address2);
    }

    @Test
    public void deleteAddress(){
        when(userRepository.findUserById(user2.getId())).thenReturn(user2);
        when(addressRepository.findAddressesById(user2.getId())).thenReturn(address2);
        addressService.deleteAddress(user2.getId());
        verify(userRepository,times(1)).findUserById(user2.getId());
        verify(addressRepository,times(1)).findAddressesById(user2.getId());
        verify(addressRepository,times(1)).delete(address2);
    }


}
