package com.jugovicm.DoctorAppointment.controller;

import com.jugovicm.DoctorAppointment.dto.DoctorDTO;
import com.jugovicm.DoctorAppointment.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/doctor")
@Tag(name = "Doctors", description = "API for managing doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Operation(summary = "Create a new doctor",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Returns the created doctor."),
                    @ApiResponse(responseCode = "400", description = "Validation error."),
                    @ApiResponse(responseCode = "401", description = "Username header missing.")
            })
    @PostMapping
    public ResponseEntity<DoctorDTO> createDoctor(
            @RequestHeader(value = "X-Username", required = true) String username,
            @Valid @RequestBody DoctorDTO dto) {
        return new ResponseEntity<>(doctorService.createDoctor(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a doctor by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns doctor details."),
                    @ApiResponse(responseCode = "404", description = "Doctor not found.")
            })
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(doctor);
    }

    @Operation(summary = "Delete a doctor",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor deleted successfully."),
                    @ApiResponse(responseCode = "404", description = "Doctor not found.")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteDoctor(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok(Map.of("message", "Doctor deleted successfully.")); // ✅ Dodali smo poruku u JSON odgovoru
    }

    @Operation(summary = "Get all doctors",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns a list of all doctors.")
            })
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors(
            @RequestHeader(value = "X-Username", required = true) String username) {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @Operation(summary = "Update doctor information",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Doctor updated successfully."),
                    @ApiResponse(responseCode = "404", description = "Doctor not found.")
            })
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(
            @RequestHeader(value = "X-Username", required = true) String username,
            @PathVariable UUID id,
            @Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.updateDoctor(id, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    //http://localhost:8080/v1/doctor/search?query=drmica
    @Operation(summary = "Search doctors",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Returns a list of doctors matching the query."),
                    @ApiResponse(responseCode = "404", description = "No doctors found for the given query.")
            })
    @GetMapping("/search")
    public ResponseEntity<Object> searchDoctors(
            @RequestHeader(value = "X-Username", required = true) String username,
            @RequestParam("query") String query) {
        List<DoctorDTO> doctors = doctorService.searchDoctors(query);

        if (doctors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No doctors found for query: " + query));
        }

        return ResponseEntity.ok(doctors);
    }
    // Search doctor, POST method,
    //@Operation(summary = "Search doctors",
    //        responses = {
    //                @ApiResponse(responseCode = "200", description = "Returns a list of doctors matching the query."),
    //                @ApiResponse(responseCode = "400", description = "Invalid query parameter.")
    //        })
    //@PostMapping("/search")
    //public ResponseEntity<List<DoctorDTO>> searchDoctors(
    //        @RequestHeader(value = "X-Username", required = true) String username,
    //        @RequestBody Map<String, String> searchParams) {
    //
    //    String searchTerm = searchParams.get("query");
    //    if (searchTerm == null || searchTerm.trim().isEmpty()) {
    //        return ResponseEntity.badRequest().body(List.of());
    //    }
    //
    //    List<DoctorDTO> doctors = doctorService.searchDoctors(searchTerm);
    //    return ResponseEntity.ok(doctors);
    //}

}
