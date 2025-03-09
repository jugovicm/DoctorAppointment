package com.jugovicm.DoctorAppointment.service;

import com.jugovicm.DoctorAppointment.dto.AppointmentRequestDTO;
import com.jugovicm.DoctorAppointment.dto.AppointmentResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    /**
     * Creates a new appointment.
     *
     * @param appointment DTO containing appointment details.
     * @return The created appointment as a DTO.
     */
    AppointmentResponseDTO createAppointment(@Valid AppointmentRequestDTO appointment);

    /**
     * Retrieves an appointment by its ID.
     *
     * @param appointmentId UUID of the appointment.
     * @return The found appointment as a DTO.
     */
    AppointmentResponseDTO getAppointment(UUID appointmentId);

    /**
     * Retrieves all appointments.
     *
     * @return A list of all appointments as DTOs.
     */
    List<AppointmentResponseDTO> getAllAppointments();

    /**
     * Retrieves all appointments for a given doctor.
     *
     * @param doctorId UUID of the doctor.
     * @return A list of appointments for the given doctor.
     */
    List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId);

    /**
     * Retrieves all appointments for a given patient.
     *
     * @param patientId UUID of the patient.
     * @return A list of appointments for the given patient.
     */
    List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId);

    /**
     * Cancels an appointment.
     *
     * @param appointmentId UUID of the appointment to cancel.
     * @return The updated appointment as a DTO.
     */
    AppointmentResponseDTO cancelAppointment(UUID appointmentId);

}
