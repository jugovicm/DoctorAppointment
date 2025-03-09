package com.jugovicm.DoctorAppointment.service.impl;

import com.jugovicm.DoctorAppointment.dto.DoctorDTO;
import com.jugovicm.DoctorAppointment.model.Appointment;
import com.jugovicm.DoctorAppointment.model.Doctor;
import com.jugovicm.DoctorAppointment.repository.DoctorRepository;
import com.jugovicm.DoctorAppointment.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    private static final Logger log = LoggerFactory.getLogger(DoctorServiceImpl.class);

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        if (doctorRepository.existsByUsername(doctorDTO.getUsername())) {
            log.warn("Attempted to create a doctor with an existing username: {}", doctorDTO.getUsername());
            throw new IllegalArgumentException("Username '" + doctorDTO.getUsername() + "' already exists.");
        }

        Doctor doctor = new Doctor();
        doctor.setUsername(doctorDTO.getUsername());
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());

        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor created successfully with ID: {}", savedDoctor.getId());

        return mapToDTO(savedDoctor);
    }

    @Override
    public DoctorDTO getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor not found with ID: {}", id);
                    return new EntityNotFoundException("Doctor not found with ID: " + id);
                });

        return mapToDTO(doctor);
    }

    @Override
    public List<DoctorDTO> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    //Cannot delete patient with existing appointments
    @Transactional
    @Override
    public void deleteDoctor(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + id));

        // Proveravamo da li doktor ima zakazane termine
        if (!doctor.getAppointments().isEmpty()) {
            throw new IllegalStateException("Cannot delete doctor with existing appointments.");
        }

        doctorRepository.delete(doctor);
        log.info("Doctor deleted successfully with ID: {}", id);
    }

    /*
    // Delete doctor from all appointments
    public void deleteDoctor(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + id));

        // Uklanjanje doktora iz svih termina pre brisanja
        for (Appointment appointment : doctor.getAppointments()) {
            appointment.getDoctors().remove(doctor);
        }

        doctorRepository.delete(doctor);
        log.info("Doctor deleted successfully with ID: {}", id);
    }
    */
    private DoctorDTO mapToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setUsername(doctor.getUsername());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        return dto;
    }
}
