# API Examples

## 🏥 Doctors API

### 📌 Create a Doctor
```http
POST /v1/doctor
X-Username: admin
Content-Type: application/json
```

```json
{
  "username": "drkovac",
  "firstName": "Milica",
  "lastName": "Kovac"
}
```

### 📌 Retrieve All Doctors
```http
GET /v1/doctor
X-Username: admin
```

### 📌 Retrieve a Doctor by ID
```http
GET /v1/doctor/18ed2e30-e7d2-4ed4-a4a5-60b4630dadd4
X-Username: admin
```

### 📌 Update a Doctor
```http
PUT /v1/doctor/18ed2e30-e7d2-4ed4-a4a5-60b4630dadd4
X-Username: admin
```

```json
{
  "username" : "drkovac"
  "firstName": "Marija",
  "lastName": "Isailovic"
}
```

### 📌 Delete a Doctor

```http
DELETE /v1/doctor/18ed2e30-e7d2-4ed4-a4a5-60b4630dadd4
X-Username: admin
```

### 📌 Search for Doctors
```http
GET /v1/doctor/search?query=Kovac
X-Username: admin
```

## 👨‍⚕️ Patients API

### 📌 Create a Patient
```http
POST /v1/patient
X-Username: admin
Content-Type: application/json
```
```json
{
  "firstName": "Ivana",
  "lastName": "Rakic",
  "middleName": "Ana",
  "dateOfBirth": "1992-08-15"
}
```
### 📌 Retrieve All Patients
```http
GET /v1/patient
X-Username: admin
```
### 📌 Retrieve a Patient by ID
```http
GET /v1/patient/de968c17-54f7-4152-af8b-f6479b962a80
X-Username: admin
```
### 📌 Update a Patient
```http
PUT /v1/patient/de968c17-54f7-4152-af8b-f6479b962a80
X-Username: admin
```
```json
{
  "firstName": "Petar",
  "lastName": "Peric",
  "middleName": "Nikola",
  "dateOfBirth": "1990-03-22"
}
```
### 📌 Delete a Patient
```http
DELETE /v1/patient/de968c17-54f7-4152-af8b-f6479b962a80
X-Username: admin
```
### 📌 Search for Patients
```http
POST /v1/patient/search
X-Username: admin
```
```json
{
  "query": "Marija"
}
```
## 📅 Appointments API

### 📌 Create an Appointment
```http
POST /v1/appointment
X-Username: drkovac
```
```json
{
  "appointmentTime": "2024-03-15T10:00:00",
  "status": "Scheduled",
  "patientId": "de968c17-54f7-4152-af8b-f6479b962a80",
  "doctorIds": ["18ed2e30-e7d2-4ed4-a4a5-60b4630dadd4"]
}
```

### 📌 Retrieve All Appointments
```http
GET /v1/appointment
X-Username: admin
```
### 📌 Retrieve an Appointment by ID
```http
GET /v1/appointment/e54e2994-ba3e-4ad3-a990-ab4b0184f8af
X-Username: admin
```
### 📌 Update an Appointment (Change Time)
```http
PUT /v1/appointment/e54e2994-ba3e-4ad3-a990-ab4b0184f8af
X-Username: drkovac
```
```json
{
  "appointmentTime": "2024-04-15T10:00:00",
  "status": "Scheduled",
  "patientId": "de968c17-54f7-4152-af8b-f6479b962a80",
  "doctorIds": ["18ed2e30-e7d2-4ed4-a4a5-60b4630dadd4"]
}
```
### 📌 Delete an Appointment
```http
DELETE /v1/appointment/e54e2994-ba3e-4ad3-a990-ab4b0184f8af
X-Username: drkovac
```
### 📌 Retrieve Appointments for a Doctor
```http
GET /v1/appointment/doctor/18ed2e30-e7d2-4ed4-a4a5-60b4630dadd4
X-Username: admin
```
### 📌 Retrieve Appointments for a Patient
```http
GET /v1/appointment/patient/de968c17-54f7-4152-af8b-f6479b962a80
X-Username: admin
```
### 📌 Cancel an Appointment
```http
PUT /v1/appointment/cancel/e54e2994-ba3e-4ad3-a990-ab4b0184f8af
X-Username: drkovac
```





