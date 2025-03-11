-- Check in pgAdmin
-- SELECT * FROM pg_indexes WHERE tablename IN ('patient', 'appointment', 'doctor_appointments');

-- Indeksiranje za bržu pretragu pacijenata
CREATE INDEX idx_patient_lastname ON patient (last_name);
CREATE INDEX idx_patient_firstname ON patient (first_name);

-- Indeksiranje termina po vremenu zakazivanja
CREATE INDEX idx_appointment_time ON appointment (appointment_time);

-- Indeksiranje sastanaka prema doktorima
CREATE INDEX idx_appointment_doctor ON doctor_appointments (doctor_id);

-- Indeksiranje sastanaka prema pacijentima
CREATE INDEX idx_appointment_patient ON appointment (patient_id);

-- Indeksiranje prema korisniku koji je kreirao termin
CREATE INDEX idx_appointment_created_by ON appointment (created_by);
