package com.jugovicm.DoctorAppointment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequestDTO {

    @NotNull(message = "Appointment time is required.")
    private LocalDateTime appointmentTime;

    @NotNull(message = "Status is required.")
    private String status;

    @NotNull(message = "Patient ID is required.")
    private UUID patientId;

    @NotNull(message = "At least one doctor ID is required.")
    private List<UUID> doctorIds;
}
