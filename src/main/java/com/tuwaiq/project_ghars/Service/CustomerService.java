package com.tuwaiq.project_ghars.Service;

import com.tuwaiq.project_ghars.Api.ApiException;
import com.tuwaiq.project_ghars.Config.Configuration;
import com.tuwaiq.project_ghars.DTOIn.CustomerDTOIn;
import com.tuwaiq.project_ghars.Model.Customer;
import com.tuwaiq.project_ghars.Model.User;
import com.tuwaiq.project_ghars.Repository.CustomerRepository;
import com.tuwaiq.project_ghars.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final Configuration configuration;

    public void registerCustomer(CustomerDTOIn customerDTOIn) {

        if (userRepository.findUserByUsername(customerDTOIn.getUsername()) != null) {
            throw new ApiException("Username already exists");
        }

        if (userRepository.findUserByEmail(customerDTOIn.getEmail()) != null) {
            throw new ApiException("Email already exists");
        }

        if (userRepository.findUserByPhoneNumber(customerDTOIn.getPhoneNumber()) != null) {
            throw new ApiException("Phone number already exists");
        }

        User user = new User();
        user.setUsername(customerDTOIn.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(customerDTOIn.getPassword()));
        user.setName(customerDTOIn.getName());
        user.setEmail(customerDTOIn.getEmail());
        user.setPhoneNumber(customerDTOIn.getPhoneNumber());
        user.setRole("CUSTOMER");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setFavorites("[]");

        customerRepository.save(customer);
    }

    public Customer getMyCustomer(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("CUSTOMER")) {
            throw new ApiException("Access denied");
        }

        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        return customer;
    }

    public void updateMyCustomer(Integer userId, CustomerDTOIn customerDTOIn) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("CUSTOMER")) {
            throw new ApiException("Access denied");
        }

        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        User checkUsername = userRepository.findUserByUsername(customerDTOIn.getUsername());
        if (checkUsername != null && !checkUsername.getId().equals(userId)) {
            throw new ApiException("Username already exists");
        }

        User checkEmail = userRepository.findUserByEmail(customerDTOIn.getEmail());
        if (checkEmail != null && !checkEmail.getId().equals(userId)) {
            throw new ApiException("Email already exists");
        }

        User checkPhone = userRepository.findUserByPhoneNumber(customerDTOIn.getPhoneNumber());
        if (checkPhone != null && !checkPhone.getId().equals(userId)) {
            throw new ApiException("Phone number already exists");
        }

        user.setUsername(customerDTOIn.getUsername());
        user.setPassword(configuration.passwordEncoder().encode(customerDTOIn.getPassword()));
        user.setName(customerDTOIn.getName());
        user.setEmail(customerDTOIn.getEmail());
        user.setPhoneNumber(customerDTOIn.getPhoneNumber());

        userRepository.save(user);
        customerRepository.save(customer);
    }

    public void deleteMyCustomer(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        if (!user.getRole().equals("CUSTOMER")) {
            throw new ApiException("Access denied");
        }

        Customer customer = customerRepository.findCustomerById(userId);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        customerRepository.delete(customer);
        userRepository.delete(user);
    }
}
