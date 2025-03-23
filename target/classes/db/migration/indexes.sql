-- SELECT * FROM pg_indexes WHERE tablename IN ('patient', 'appointment', 'doctor_appointments');

-- Indeksiranje za pretragu pacijenata
CREATE INDEX IF NOT EXISTS idx_patient_lastname ON patient (last_name);
CREATE INDEX IF NOT EXISTS idx_patient_firstname ON patient (first_name);

-- Indeksiranje termina po vremenu zakazivanja
CREATE INDEX IF NOT EXISTS idx_appointment_time ON appointment (appointment_time);

-- Indeksiranje prema pacijentima
CREATE INDEX IF NOT EXISTS idx_appointment_patient ON appointment (patient_id);

-- Indeksiranje prema korisniku koji je kreirao termin
CREATE INDEX IF NOT EXISTS idx_appointment_created_by ON appointment (created_by);

-- Indeks za veznu tabelu između doktora i sastanaka
CREATE INDEX IF NOT EXISTS idx_doctor_appointments_doctor ON doctor_appointments (doctor_id);
CREATE INDEX IF NOT EXISTS idx_doctor_appointments_appointment ON doctor_appointments (appointment_id);
