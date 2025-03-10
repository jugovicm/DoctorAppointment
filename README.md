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

4️⃣ ## 📌 API Examples

For detailed API request examples, see API Examples.

🔐 Authentication & Authorization

No real authentication is required.
API uses X-Username header to verify doctor identity.
A doctor can only cancel their own scheduled appointments.
If X-Username is missing, API returns 401 Unauthorized.

📜 License

This project is licensed under the MIT License - see the LICENSE file for details.
 
