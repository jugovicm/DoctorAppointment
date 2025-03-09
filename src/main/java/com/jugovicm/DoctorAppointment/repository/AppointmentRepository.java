package com.jugovicm.DoctorAppointment.repository;

import com.jugovicm.DoctorAppointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatient_Id(UUID patientId);
    List<Appointment> findByDoctors_Id(UUID doctorId);
}

