package com.jugovicm.DoctorAppointment.controller;

import com.jugovicm.DoctorAppointment.dto.PatientDTO;
import com.jugovicm.DoctorAppointment.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/patient")
@Tag(name = "Patients", description = "API for managing patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Create a new patient",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns the created patient."),
                    @ApiResponse(responseCode = "400", description = "Validation error."),
                    @ApiResponse(responseCode = "401", description = "Username header missing.")
            })
    @PostMapping
    public ResponseEntity<PatientDTO> createPatient(
            @RequestHeader(value = "X-Username", required = true) String username,
            @Valid @RequestBody PatientDTO dto) {
        return new ResponseEntity<>(patientService.createPatient(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a patient",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Patient deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Patient not found.")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePatient(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok(Map.of("message", "Patient deleted successfully"));
    }
    @PostMapping("/search")
    public ResponseEntity<Object> searchPatients(
            @RequestHeader(value = "X-Username", required = true) String username,
            @RequestBody Map<String, String> searchParams) {

        String searchTerm = searchParams.get("query");
        List<PatientDTO> patients = patientService.searchPatients(searchTerm);

        if (patients.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No patients found for query: " + searchTerm));
        }

        return ResponseEntity.ok(patients);
    }
}
