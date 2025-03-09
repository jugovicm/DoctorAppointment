package com.jugovicm.DoctorAppointment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DoctorDTO {
    private UUID id;

    @NotNull(message = "Username cannot be null")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,32}$", message = "Username must be 4-32 characters long and contain only letters, numbers, and underscores")
    private String username;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
}
