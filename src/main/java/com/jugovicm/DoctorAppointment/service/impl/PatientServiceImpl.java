package com.jugovicm.DoctorAppointment.service.impl;

import com.jugovicm.DoctorAppointment.dto.PatientDTO;
import com.jugovicm.DoctorAppointment.model.Appointment;
import com.jugovicm.DoctorAppointment.model.Patient;
import com.jugovicm.DoctorAppointment.repository.PatientRepository;
import com.jugovicm.DoctorAppointment.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        Patient patient = new Patient();
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setMiddleName(patientDTO.getMiddleName());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());

        Patient savedPatient = patientRepository.save(patient);
        log.info("Patient created successfully with ID: {}", savedPatient.getId());

        return mapToDTO(savedPatient);
    }

    @Override
    public PatientDTO getPatientById(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Patient not found with ID: {}", id);
                    return new EntityNotFoundException("Patient not found with ID: " + id);
                });

        return mapToDTO(patient);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    //Cannot delete patient with existing appointments
    @Transactional
    @Override
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + id));

        // Proveravamo da li pacijent ima zakazane termine
        if (!patient.getAppointments().isEmpty()) {
            throw new IllegalStateException("Cannot delete patient with existing appointments.");
        }

        patientRepository.delete(patient);
        log.info("Patient deleted successfully with ID: {}", id);
    }

    //Delete patient from all appointments
    /*
    // @Transactional
    @Override
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + id));

        // Uklanjamo pacijenta iz svih zakazanih termina pre nego što ga obrišemo
        for (Appointment appointment : patient.getAppointments()) {
            appointment.setPatient(null); // Uklanjamo referencu na pacijenta
        }

        // Brisanje svih termina pacijenta pre nego što ga obrišemo,  ukljuciti @Transactional !!!!
        if (!patient.getAppointments().isEmpty()) {
            appointmentRepository.deleteAll(patient.getAppointments());
        }

        patientRepository.delete(patient);
        log.info("Patient deleted successfully with ID: {}", id);
    }
    */
    @Override
    public List<PatientDTO> searchPatients(String searchTerm) {
        List<Patient> patients = patientRepository.searchPatients(searchTerm.toLowerCase());

        return patients.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private PatientDTO mapToDTO(Patient patient) {
        PatientDTO dto = new PatientDTO();
        dto.setId(patient.getId());
        dto.setFirstName(patient.getFirstName());
        dto.setLastName(patient.getLastName());
        dto.setMiddleName(patient.getMiddleName());
        dto.setDateOfBirth(patient.getDateOfBirth());
        return dto;
    }

    @Transactional
    @Override
    public PatientDTO updatePatient(UUID id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + id));

        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setMiddleName(patientDTO.getMiddleName());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());

        Patient updatedPatient = patientRepository.save(patient);
        log.info("Patient updated successfully with ID: {}", updatedPatient.getId());

        return mapToDTO(updatedPatient);
    }

    @Override
    public Page<PatientDTO> getAllPatients(Pageable pageable) {
        return patientRepository.findAll(pageable)
                .map(patient -> {
                    PatientDTO dto = new PatientDTO();
                    dto.setId(patient.getId());
                    dto.setFirstName(patient.getFirstName());
                    dto.setLastName(patient.getLastName());
                    dto.setMiddleName(patient.getMiddleName());
                    dto.setDateOfBirth(patient.getDateOfBirth());
                    return dto;
                });
    }

}
