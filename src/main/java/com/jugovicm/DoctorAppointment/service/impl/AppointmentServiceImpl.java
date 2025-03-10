package com.jugovicm.DoctorAppointment.service.impl;

import com.jugovicm.DoctorAppointment.dto.AppointmentRequestDTO;
import com.jugovicm.DoctorAppointment.dto.AppointmentResponseDTO;
import com.jugovicm.DoctorAppointment.dto.DoctorDTO;
import com.jugovicm.DoctorAppointment.dto.PatientDTO;
import com.jugovicm.DoctorAppointment.model.Appointment;
import com.jugovicm.DoctorAppointment.model.Doctor;
import com.jugovicm.DoctorAppointment.model.Patient;
import com.jugovicm.DoctorAppointment.repository.AppointmentRepository;
import com.jugovicm.DoctorAppointment.repository.DoctorRepository;
import com.jugovicm.DoctorAppointment.repository.PatientRepository;
import com.jugovicm.DoctorAppointment.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private static final Logger log = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    @Override
    public AppointmentResponseDTO createAppointment(@Valid AppointmentRequestDTO dto, String username) {
        log.info("Creating appointment for patient ID: {}", dto.getPatientId());

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        List<Doctor> doctors = doctorRepository.findAllById(dto.getDoctorIds());
        if (doctors.isEmpty() || doctors.size() != dto.getDoctorIds().size()) {
            throw new EntityNotFoundException("One or more doctors not found.");
        }

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setStatus(dto.getStatus());
        appointment.setPatient(patient);
        appointment.setDoctors(doctors);
        appointment.setCreatedBy(username);

        appointment = appointmentRepository.save(appointment);
        log.info("Successfully created appointment with ID: {}", appointment.getId());

        return mapToResponseDTO(appointment);
    }


    @Override
    public AppointmentResponseDTO getAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment with ID " + appointmentId + " not found"));

        return mapToResponseDTO(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId) {
        return appointmentRepository.findByDoctors_Id(doctorId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId) {
        return appointmentRepository.findByPatient_Id(patientId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public AppointmentResponseDTO cancelAppointment(UUID appointmentId, String username) throws AccessDeniedException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Provera autorizacije
        if (!appointment.getCreatedBy().equals(username)) {
            throw new AccessDeniedException("You are not authorized to cancel this appointment.");
        }

        appointment.setStatus("Cancelled");
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment with ID {} has been cancelled.", appointmentId);

        return mapToResponseDTO(updatedAppointment);
    }



    private AppointmentResponseDTO mapToResponseDTO(Appointment appointment) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(appointment.getId());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setStatus(appointment.getStatus());
        dto.setCreatedBy(appointment.getCreatedBy());

        // Map patient in PatientDTO
        if (appointment.getPatient() != null) {
            PatientDTO patientDTO = new PatientDTO();
            patientDTO.setId(appointment.getPatient().getId());
            patientDTO.setFirstName(appointment.getPatient().getFirstName());
            patientDTO.setLastName(appointment.getPatient().getLastName());
            patientDTO.setMiddleName(appointment.getPatient().getMiddleName());
            patientDTO.setDateOfBirth(appointment.getPatient().getDateOfBirth());
            dto.setPatient(patientDTO);
        } else {
            dto.setPatient(null); // Ako nema pacijenta
        }

        // Map doctor in DoctorDTO list
        if (appointment.getDoctors() != null && !appointment.getDoctors().isEmpty()) {
            List<DoctorDTO> doctorDTOs = appointment.getDoctors().stream().map(doctor -> {
                DoctorDTO doctorDTO = new DoctorDTO();
                doctorDTO.setId(doctor.getId());
                doctorDTO.setFirstName(doctor.getFirstName());
                doctorDTO.setLastName(doctor.getLastName());
                doctorDTO.setUsername(doctor.getUsername());
                return doctorDTO;
            }).collect(Collectors.toList());

            dto.setDoctors(doctorDTOs);
        } else {
            dto.setDoctors(List.of()); // If doctor does not exist
        }

        return dto;
    }

    @Transactional
    @Override
    public void deleteAppointment(UUID appointmentId, String username) throws AccessDeniedException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Provera autorizacije
        if (!appointment.getCreatedBy().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this appointment.");
        }

        appointmentRepository.delete(appointment);
        log.info("Appointment with ID {} deleted successfully.", appointmentId);
    }


    @Transactional
    @Override
    public AppointmentResponseDTO updateAppointment(UUID appointmentId, AppointmentRequestDTO dto, String username) throws AccessDeniedException {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // Provera autorizacije
        if (!appointment.getCreatedBy().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this appointment.");
        }

        // Ažuriranje samo datuma i vremena
        if (dto.getAppointmentTime() == null) {
            throw new IllegalArgumentException("Appointment time must be provided for update.");
        }

        appointment.setAppointmentTime(dto.getAppointmentTime());

        Appointment updatedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment with ID {} updated successfully.", appointmentId);

        return mapToResponseDTO(updatedAppointment);
    }
}
