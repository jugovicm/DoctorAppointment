package com.jugovicm.DoctorAppointment.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class AppointmentResponseDTO {
    private UUID id;
    private LocalDateTime appointmentTime;
    private String status;
    private PatientDTO patient;
    private List<DoctorDTO> doctors;
}
