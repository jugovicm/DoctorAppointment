package com.jugovicm.DoctorAppointment.service;

import com.jugovicm.DoctorAppointment.dto.PatientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    /**
     * Creates a new patient.
     *
     * @param patientDTO DTO containing patient details.
     * @return The created patient as a DTO.
     */
    PatientDTO createPatient(PatientDTO patientDTO);

    /**
     * Retrieves a patient by their ID.
     *
     * @param id UUID of the patient.
     * @return The found patient as a DTO.
     */
    PatientDTO getPatientById(UUID id);

    /**
     * Retrieves all patients.
     *
     * @return A list of all patients as DTOs.
     */
    List<PatientDTO> getAllPatients();

    /**
     * Deletes a patient by ID.
     *
     * @param id UUID of the patient to delete.
     */
    void deletePatient(UUID id);

    /**
     * Searches for patients by first name, last name, or middle name.
     * The search is case-insensitive and supports partial matches.
     *
     * @param searchTerm The term used for searching patients.
     * @return A list of matching patients as DTOs.
     */
    List<PatientDTO> searchPatients(String searchTerm);

    /**
     * Update patient.
     *
     * @param patientDTO DTO containing updated patient details.
     * @param id UUID of the patient to delete.
     * @return The updated patient as a DTO.
     */
    PatientDTO updatePatient(UUID id, PatientDTO patientDTO);
    /**
     * Retrieves a paginated list of all patients.
     *
     * @param pageable Pagination and sorting information.
     * @return A paginated page of patients as DTOs.
     */
    Page<PatientDTO> getAllPatients(Pageable pageable);

}
