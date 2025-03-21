package com.jugovicm.DoctorAppointment.repository;

import com.jugovicm.DoctorAppointment.model.Appointment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    @EntityGraph(attributePaths = {"patient", "doctors"})
    List<Appointment> findAll();
    @EntityGraph(attributePaths = {"patient", "doctors"})
    Optional<Appointment> findById(UUID id);
    @EntityGraph(attributePaths = {"patient", "doctors"})
    List<Appointment> findByPatient_Id(UUID patientId);
    @EntityGraph(attributePaths = {"patient", "doctors"})
    List<Appointment> findByDoctors_Id(UUID doctorId);
}

