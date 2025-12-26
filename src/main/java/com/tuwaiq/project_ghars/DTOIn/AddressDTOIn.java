package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressDTOIn {

    @NotEmpty(message = "Sorry, the country can't be empty, please try again")
    private String country;
    @NotEmpty(message = "Sorry, the city can't be empty, please try again")
    private String city;
    @NotEmpty(message = "Sorry, the street can't be empty, please try again")
    private String street;
    @NotEmpty(message = "Sorry, the building number can't be empty, please try again")
    private String buildingNumber;
    @NotEmpty(message = "Sorry, the postal number can't be empty, please try again")
    private String postalNumber;
    private Integer userId;
}
