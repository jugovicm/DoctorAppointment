package com.jugovicm.DoctorAppointment.dto;

import com.jugovicm.DoctorAppointment.model.AppointmentStatus;
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
    private AppointmentStatus status;
    private PatientDTO patient;
    private List<DoctorDTO> doctors;
    private String createdBy;
}
