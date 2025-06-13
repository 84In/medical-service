package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.SpecialtyDto;
import com.vasd.medical_service.doctors.service.SpecialtyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialties")
@RequiredArgsConstructor
@Tag(name = "Specialty Controller", description = "Doctor Specialty Management")
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    @GetMapping
    @Operation(summary = "Get list of all specialty information", description = "Return the list of specialties available in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Specialty List")
    public ApiResponse<List<SpecialtyDto>> getSpecialties() {
        return ApiResponse.<List<SpecialtyDto>>builder()
                .result(specialtyService.getAllSpecialties())
                .build();
    }

    @GetMapping("/{specialtyId}")
    @Operation(summary = "Get specialty information by id", description = "Return specialty information by id")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Specialty information")
    public ApiResponse<SpecialtyDto> getSpecialty(@PathVariable Long specialtyId) {
        return ApiResponse.<SpecialtyDto>builder()
                .result(specialtyService.getSpecialty(specialtyId))
                .build();
    }

    @GetMapping("/search/{keyword}")
    @Operation(summary = "Get list of all specialty by keyword", description = "Return the list of specialties available in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Specialty list")
    public ApiResponse<List<SpecialtyDto>> searchSpecialty(@PathVariable String keyword) {
        return ApiResponse.<List<SpecialtyDto>>builder()
                .result(specialtyService.getAllSpecialtyByName(keyword))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new specialty information", description = "Return the newly created specialty information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created specialty informaiton")
    public ApiResponse<SpecialtyDto> createSpecialty(@Valid @RequestBody SpecialtyDto request) {

        return ApiResponse.<SpecialtyDto>builder()
                .result(specialtyService.createSpecialty(request))
                .build();
    }

    @PutMapping("/{specialtyId}")
    @Operation(summary = "Update specialty information by ID", description = "Return the recently updated specialty information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",description = "Recently updated specialty information")
    public ApiResponse<SpecialtyDto> updateSpecialty(@PathVariable Long specialtyId, @Valid @RequestBody SpecialtyDto request) {
        return ApiResponse.<SpecialtyDto>builder()
                .result(specialtyService.updateSpecialty(specialtyId, request))
                .build();
    }

    @DeleteMapping("/{specialtyId}")
    @Operation(summary = "Delete specialty information by ID", description = "Return successful deletion status")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> deleteSpecialty(@PathVariable Long specialtyId) {
        specialtyService.deleteSpecialty(specialtyId);
        return ApiResponse.<Void>builder()
                .message("Deleted successfully")
                .build();
    }



}
