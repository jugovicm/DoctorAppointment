package com.jugovicm.DoctorAppointment.service;
import com.jugovicm.DoctorAppointment.dto.DoctorDTO;

import java.util.List;
import java.util.UUID;

public interface DoctorService {

    /**
     * Creates a new doctor.
     *
     * @param doctorDTO DTO containing doctor details.
     * @return The created doctor as a DTO.
     */
    DoctorDTO createDoctor(DoctorDTO doctorDTO);

    /**
     * Retrieves a doctor by their ID.
     *
     * @param id UUID of the doctor.
     * @return The found doctor as a DTO.
     */
    DoctorDTO getDoctorById(UUID id);

    /**
     * Retrieves all doctors.
     *
     * @return A list of all doctors as DTOs.
     */
    List<DoctorDTO> getAllDoctors();

    /**
     * Deletes a doctor by ID.
     *
     * @param id UUID of the doctor to delete.
     */
    void deleteDoctor(UUID id);
}


