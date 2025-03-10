package com.jugovicm.DoctorAppointment.controller;

import com.jugovicm.DoctorAppointment.dto.AppointmentRequestDTO;
import com.jugovicm.DoctorAppointment.dto.AppointmentResponseDTO;
import com.jugovicm.DoctorAppointment.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/appointment")
@Tag(name = "Appointments", description = "API for managing doctor appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Create a new appointment
     */
    @Operation(summary = "Create a new appointment",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns the created appointment."),
                    @ApiResponse(responseCode = "400", description = "Validation error."),
                    @ApiResponse(responseCode = "401", description = "Username header missing.")
            })
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @RequestHeader(value = "X-Username", required = true) String username,
            @Valid @RequestBody AppointmentRequestDTO dto) {

        AppointmentResponseDTO appointmentResponse = appointmentService.createAppointment(dto, username);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.CREATED);
    }

    /**
     * Retrieve an appointment by ID
     */
    @Operation(summary = "Retrieve an appointment by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns appointment details."),
                    @ApiResponse(responseCode = "404", description = "Appointment not found.")
            })
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> getAppointment(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID appointmentId) {

        AppointmentResponseDTO appointmentResponse = appointmentService.getAppointment(appointmentId);
        return ResponseEntity.ok(appointmentResponse);
    }

    /**
     * Cancel an appointment
     */
    @Operation(summary = "Cancel an appointment",
            description = "Only the doctor who created the appointment can cancel it.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment canceled successfully."),
                    @ApiResponse(responseCode = "403", description = "User not authorized to cancel this appointment."),
                    @ApiResponse(responseCode = "404", description = "Appointment not found.")
            })
    @PutMapping("/cancel/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> cancelAppointment(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID appointmentId) throws AccessDeniedException {

        return ResponseEntity.ok(appointmentService.cancelAppointment(appointmentId, username));
    }

    /**
     * Retrieve all appointments
     */
    @Operation(summary = "Retrieve all appointments",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns a list of appointments.")
            })
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments(
            @RequestHeader(value = "X-Username", required = true) String username) {

        List<AppointmentResponseDTO> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieve all appointments for a doctor
     */
    @Operation(summary = "Retrieve all appointments by doctor ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns a list of appointments for the specified doctor."),
                    @ApiResponse(responseCode = "404", description = "Doctor not found or no appointments found.")
            })
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<Object> getAppointmentsByDoctor(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID doctorId) {

        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByDoctor(doctorId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No appointments found for doctor ID: " + doctorId));
        }

        return ResponseEntity.ok(appointments);
    }

    /**
     * Retrieve all appointments for a patient
     */
    @Operation(summary = "Retrieve all appointments by patient ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns a list of appointments for the specified patient."),
                    @ApiResponse(responseCode = "404", description = "Patient not found or no appointments found.")
            })
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Object> getAppointmentsByPatient(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID patientId) {

        List<AppointmentResponseDTO> appointments = appointmentService.getAppointmentsByPatient(patientId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No appointments found for patient ID: " + patientId));
        }

        return ResponseEntity.ok(appointments);
    }

    /**
     * Delete an appointment
     */

    @Operation(summary = "Delete an appointment",
            description = "Only the doctor who created the appointment can delete it.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Appointment deleted successfully."),
                    @ApiResponse(responseCode = "403", description = "User not authorized to delete this appointment."),
                    @ApiResponse(responseCode = "404", description = "Appointment not found.")
            })
    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Map<String, String>> deleteAppointment(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID appointmentId) throws AccessDeniedException {
        appointmentService.deleteAppointment(appointmentId, username);
        return ResponseEntity.ok(Map.of("message", "Appointment deleted successfully."));
    }


    /**
     * Update an appointment
     */
    @Operation(summary = "Update appointment date and time",
            description = "Only the doctor who created the appointment can update the appointment, and only the date and time can be changed.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Appointment updated successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid appointment time."),
                    @ApiResponse(responseCode = "403", description = "User not authorized to update this appointment."),
                    @ApiResponse(responseCode = "404", description = "Appointment not found.")
            })
    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID appointmentId,
            @Valid @RequestBody AppointmentRequestDTO dto) throws AccessDeniedException {
        return ResponseEntity.ok(appointmentService.updateAppointment(appointmentId, dto, username));
    }


}
