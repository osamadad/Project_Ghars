package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DriverDTOIn {

    @NotEmpty
    @Size(min = 4, max = 10)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotEmpty
    @Size(min = 2, max = 20)
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^05\\d{8}$")
    private String phoneNumber;
}
