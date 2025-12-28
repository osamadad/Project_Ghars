package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentRequestDTOIn {

    @NotEmpty(message = "Sorry, the payment card name can't be empty, please try again")
    private String name;
    @NotEmpty(message = "Sorry, the payment card number can't be empty, please try again")
    @Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$", message = "Sorry, the payment card number must be numbers, please try again")
    private String number;
    @NotEmpty(message = "Sorry, the payment cvc can't be empty, please try again")
    @Size(min = 3, max = 3, message = "Sorry, the payment cvc can't be more or less than 3 characters, please try again")
    private String cvc;
    @NotEmpty(message = "Sorry, the payment month can't be empty, please try again")
    @Size(min = 2, max = 2, message = "Sorry, the payment mount can't be more or less than 2 characters, please try again")
    private String month;
    @NotEmpty(message = "Sorry, the payment year can't be empty, please try again")
    @Size(min = 2, max = 4, message = "Sorry, the payment year can't be less than 2 or more than 4 characters, please try again")
    private String year;
}
