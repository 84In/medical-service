package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateDoctorDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDoctorDto;
import com.vasd.medical_service.doctors.dto.response.DoctorResponseDto;
import com.vasd.medical_service.doctors.entities.Doctor;
import com.vasd.medical_service.doctors.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor Controller", description = "Doctor Profile Management")
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    @Operation(summary = "Fetch Doctor List", description = "Get detailed profiles of all doctors in the system.")
    @io.swagger.v3.oas.annotations.responses
            .ApiResponse(responseCode = "0", description = "Doctor Information List")
    public ApiResponse<List<DoctorResponseDto>> getDoctors() {
        return ApiResponse.<List<DoctorResponseDto>>builder()
                .result(doctorService.getAllDoctors())
                .build();
    }

    @GetMapping("/{doctorId}")
    @Operation(summary = "Fetch doctor details by ID", description = "Return doctor information")
    @io.swagger.v3.oas.annotations.responses
            .ApiResponse(responseCode = "0", description = "Doctor Information")
    public ApiResponse<DoctorResponseDto> getDoctor(@PathVariable Long doctorId) {
        return ApiResponse.<DoctorResponseDto>builder()
                .result(doctorService.getDoctor(doctorId))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new doctor information", description = "Return the newly created doctor information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "New doctor information")
    public ApiResponse<DoctorResponseDto> createDoctor(CreateDoctorDto request) {
        return ApiResponse.<DoctorResponseDto>builder()
                .result(doctorService.createDoctor(request))
                .build();
    }

    @PutMapping("/{doctorId}")
    @Operation(summary = "Update doctor information by ID", description = "Return the recently updated doctor information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated doctor information")
    public ApiResponse<DoctorResponseDto> updateDoctor(@PathVariable Long doctorId, UpdateDoctorDto request) {
        return ApiResponse.<DoctorResponseDto>builder()
                .result(doctorService.updateDoctor(doctorId, request))
                .build();
    }

    @DeleteMapping("/{doctorId}")
    @Operation(summary = "Delete doctor information by ID", description = "Return successful deletion message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ApiResponse.<Void>builder().message("Deleted successfully").build();
    }
}
