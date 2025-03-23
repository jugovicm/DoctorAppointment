package com.jugovicm.DoctorAppointment.service;

import com.jugovicm.DoctorAppointment.dto.AppointmentRequestDTO;
import com.jugovicm.DoctorAppointment.dto.AppointmentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    /**
     * Creates a new appointment.
     *
     * @param appointment DTO containing appointment details.
     * @param username Username of the doctor creating the appointment.
     * @return The created appointment as a DTO.
     */
    AppointmentResponseDTO createAppointment(@Valid AppointmentRequestDTO appointment, String username);

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
     * @param username Username of the doctor requesting the cancellation.
     * @return The updated appointment as a DTO.
     * @throws AccessDeniedException If the user is not authorized to cancel the appointment.
     */
    AppointmentResponseDTO cancelAppointment(UUID appointmentId, String username) throws AccessDeniedException;

    /**
     * Deletes an appointment.
     *
     * @param appointmentId UUID of the appointment to delete.
     * @param username Username of the doctor requesting the deletion.
     * @throws AccessDeniedException If the user is not authorized to delete the appointment.
     */
    void deleteAppointment(UUID appointmentId, String username) throws AccessDeniedException;

    /**
     * Updates an appointment (only date and time).
     *
     * @param appointmentId UUID of the appointment to update.
     * @param dto DTO containing appointment details.
     * @param username Username of the doctor requesting the update.
     * @return The updated appointment as a DTO.
     * @throws AccessDeniedException If the user is not authorized to update the appointment.
     */
    AppointmentResponseDTO updateAppointment(UUID appointmentId, AppointmentRequestDTO dto, String username) throws AccessDeniedException;

    /**
     * Retrieves a paginated list of all appointments.
     *
     * @param pageable Pagination and sorting information.
     * @return A paginated page of appointments as DTOs.
     */
    Page<AppointmentResponseDTO> getAllAppointments(Pageable pageable);

    /**
     * Retrieves a paginated list of appointments for a specific doctor.
     *
     * @param doctorId UUID of the doctor.
     * @param pageable Pagination and sorting information.
     * @return A paginated page of appointments for the specified doctor.
     */
    Page<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId, Pageable pageable);
}
