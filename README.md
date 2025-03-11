# 🏥 Doctor Appointment Management API

## 📖 Overview
Doctor Appointment Management API is a RESTful service that allows doctors and patients to manage medical appointments. The system enables doctors to create, update, and cancel appointments, while patients can view their scheduled visits. 

This project follows **RESTful principles**, ensuring proper HTTP response codes and API design. The system stores data in **PostgreSQL** and is built using **Spring Boot**.

## ✨ Features
✅ **CRUD operations for doctors, patients, and appointments**  
✅ **Schedule and cancel appointments**  
✅ **Retrieve appointments by ID, doctor, or patient**  
✅ **Strict role-based actions using `X-Username` header**  
✅ **Validation and error handling for request integrity**  
✅ **Scalable PostgreSQL storage for large datasets**  
✅ **OpenAPI documentation for easy API usage**  

------

## ⚙️ Tech Stack
| Technology  | Purpose  |
|-------------|-----------|
| **Java 21** | Backend language|
| **Spring Boot** | Web framework |
| **Spring Data JPA** | Database access layer |
| **PostgreSQL** | Database storage |
| **Jakarta Validation** | Input validation |
| **Hibernate** | ORM for entity management |
| **Swagger (OpenAPI 3)** | API documentation |

---

## 🚀 Getting Started

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/jugovicm/DoctorAppointment.git)
cd doctor-appointment-api

### 2️⃣ Set Up PostgreSQL Database
```bash
CREATE DATABASE doctor_appointment_db;
```
Update application.properties (or application.yml) with your PostgreSQL credentials:
```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/doctor_appointment_db
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=update
```
3️⃣ Run the Application

Use Maven to build and run the project:
```bash
mvn spring-boot:run
```
The API will be available at:
http://localhost:8080

## 📌 API Endpoint Overview

### 🏥 Doctors API

| Method | Endpoint | Description |
|--------|--------------------------|------------------------------|
| **POST** | `/v1/doctor` | Create a new doctor |
| **GET** | `/v1/doctor` | Retrieve all doctors |
| **GET** | `/v1/doctor/{id}` | Retrieve a doctor by ID |
| **PUT** | `/v1/doctor/{id}` | Update a doctor's information |
| **DELETE** | `/v1/doctor/{id}` | Delete a doctor |
| **GET** | `/v1/doctor/search?query=XXXX` | Search for doctors |

---

### 👨‍⚕️ Patients API

| Method | Endpoint | Description |
|--------|--------------------------|------------------------------|
| **POST** | `/v1/patient` | Create a new patient |
| **GET** | `/v1/patient` | Retrieve all patients |
| **GET** | `/v1/patient/{id}` | Retrieve a patient by ID |
| **PUT** | `/v1/patient/{id}` | Update a patient's information |
| **DELETE** | `/v1/patient/{id}` | Delete a patient |
| **POST** | `/v1/patient/search` | Search for patients (JSON body) |

---

### 📅 Appointments API

| Method | Endpoint | Description |
|--------|--------------------------------|--------------------------------|
| **POST** | `/v1/appointment` | Create a new appointment |
| **GET** | `/v1/appointment` | Retrieve all appointments |
| **GET** | `/v1/appointment/{id}` | Retrieve an appointment by ID |
| **PUT** | `/v1/appointment/{id}` | Update an appointment (change time) |
| **DELETE** | `/v1/appointment/{id}` | Delete an appointment |
| **GET** | `/v1/appointment/doctor/{id}` | Retrieve all appointments for a doctor |
| **GET** | `/v1/appointment/patient/{id}` | Retrieve all appointments for a patient |
| **PUT** | `/v1/appointment/cancel/{id}` | Cancel an appointment |

---

✅ **Notes:**
- Every request **must** include the `X-Username` header.
- **Search for doctors** uses `GET /v1/doctor/search?query=XXXX`
- **Search for patients** uses `POST /v1/patient/search` with a JSON body.
- Only the creator of an appointment can modify or cancel it.
- Attempting to update or delete an appointment without permission returns a 403 Forbidden error.

For detailed API request examples, see API Examples.

🔐 Authentication & Authorization

No real authentication is required.
API uses X-Username header to verify doctor identity.
Only the doctor who created the appointment can cancel, update, or delete it.
If X-Username is missing, API returns 401 Unauthorized.

## 📌 Performance Optimizations
The application is optimized for handling large amounts of data (millions of patients and appointments). 

### ✅ Indexing for Faster Queries
Indexes have been manually added in PostgreSQL using **pgAdmin** to optimize database performance:

| Table        | Indexed Column         | Purpose |
|-------------|----------------------|---------|
| `patient`   | `last_name`          | Faster patient search by last name |
| `appointment` | `appointment_time` | Faster search for appointments by time |
| `doctor_appointments` | `doctor_id` | Faster lookup of appointments by doctor |
| `appointment` | `patient_id` | Faster lookup of appointments by patient |
| `appointment` | `created_by` | Faster filtering by the user who created the appointment |

Indexes improve query execution times significantly when working with large datasets.

### ✅ Additional Performance Enhancements (Planned)
- **Table Partitioning**: Splitting large tables into smaller partitions (e.g., by year) for better performance.
- **Pagination**: API responses return data in smaller pages to improve efficiency.
- **Caching with Redis**: Caching frequently accessed data (e.g., patients and appointments) to reduce database load.

📜 License

This project is licensed under the MIT License - see the LICENSE file for details.
 
