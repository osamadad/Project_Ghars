package com.tuwaiq.project_ghars.DTOIn;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LicenseDTOIn {

    @NotEmpty(message = "License number is required")
    @Pattern(regexp = "^[A-Za-z0-9-]{5,30}$", message = "Invalid license format")
    private String license;
}
