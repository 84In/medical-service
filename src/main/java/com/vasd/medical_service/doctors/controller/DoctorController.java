package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateDoctorDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDoctorDto;
import com.vasd.medical_service.doctors.dto.response.DoctorResponseDto;
import com.vasd.medical_service.doctors.service.DoctorService;
import com.vasd.medical_service.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
    @GetMapping("/search")
    @Operation(
            summary = "Search doctors with pagination",
            description = "Returns a paginated list of doctors filtered by keyword. Keyword can match name, department, introduction, or title.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (default is 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of records per page (default is 10)", example = "10"),
                    @Parameter(name = "keyword", description = "Search keyword (name, department, introduction, or title)", example = "cardiology")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Paginated list of doctors returned successfully")
            }
    )
    public ApiResponse<PaginatedResponse<DoctorResponseDto>> getDoctors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long departmentId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id")); 
        Page<DoctorResponseDto> doctorDtos = doctorService.getAllDoctors(pageable, keyword, status, departmentId);
        return ApiResponse.<PaginatedResponse<DoctorResponseDto>>builder()
                .result(new PaginatedResponse<>(doctorDtos))
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
    public ApiResponse<DoctorResponseDto> createDoctor(@RequestBody @Valid CreateDoctorDto request) {
        return ApiResponse.<DoctorResponseDto>builder()
                .result(doctorService.createDoctor(request))
                .build();
    }

    @PutMapping("/{doctorId}")
    @Operation(summary = "Update doctor information by ID", description = "Return the recently updated doctor information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated doctor information")
    public ApiResponse<DoctorResponseDto> updateDoctor(@PathVariable Long doctorId, @RequestBody UpdateDoctorDto request) {
        log.info("Received request to update doctor information id {}", doctorId);
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
