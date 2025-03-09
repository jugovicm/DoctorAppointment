package com.jugovicm.DoctorAppointment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class PatientDTO {
        private UUID id;

        @NotNull(message = "First name cannot be null")
        @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
        private String firstName;

        @NotNull(message = "Last name cannot be null")
        @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
        private String lastName;

        @NotNull(message = "Middle name cannot be null")
        @Size(min = 2, max = 50, message = "Middle name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-z]+$", message = "Middle name must contain only letters")
        private String middleName;

        @NotNull(message = "Date of birth cannot be null")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate dateOfBirth;
}
