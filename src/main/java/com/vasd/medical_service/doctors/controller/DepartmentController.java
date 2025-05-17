package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateDepartmentDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDepartmentDto;
import com.vasd.medical_service.doctors.dto.response.DepartmentResponseDto;
import com.vasd.medical_service.doctors.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/departments")
@RequiredArgsConstructor
@Tag(name = "Department Controller", description = "Specialty Information Management")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "Fetch list of all specialties", description = "Return the list of departments currently available in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Specialty List")
    @GetMapping
    public ApiResponse<List<DepartmentResponseDto>> getDepartments() {
        return ApiResponse.<List<DepartmentResponseDto>>builder()
                .result(departmentService.getAllDepartments())
                .build();
    }

    @Operation(summary = "Get specialty information by ID", description = "Return specialty information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Specialty Information")
    @GetMapping("/{departmentId}")
    public ApiResponse<DepartmentResponseDto> getDepartment(@PathVariable Long departmentId) {

        return ApiResponse.<DepartmentResponseDto>builder()
                .result(departmentService.getDepartment(departmentId))
                .build();
    }

    @Operation(summary = "Create new specialty", description = "Return the newly created specialty information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created specialty information")
    @PostMapping
    public ApiResponse<DepartmentResponseDto> createDepartment(@RequestBody @Valid CreateDepartmentDto request) {
        return ApiResponse.<DepartmentResponseDto>builder()
                .result(departmentService.createDepartment(request))
                .build();
    }

    @Operation(summary = "Update specialty information by ID", description = "Return the updated specialty information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Updated specialty information")
    @PutMapping("/{departmentId}")
    public ApiResponse<DepartmentResponseDto> updateDepartment(@PathVariable Long departmentId, @RequestBody UpdateDepartmentDto request) {

        return ApiResponse.<DepartmentResponseDto>builder()
                .result(departmentService.updateDepartment(departmentId, request))
                .build();
    }

    @Operation(summary = "Delete specialty by specialty ID", description = "Return successful deletion status")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    @DeleteMapping("/{departmentId}")
    public ApiResponse<Void> deleteDepartment(@PathVariable Long departmentId) {

        departmentService.deleteDepartment(departmentId);
        return ApiResponse.<Void>builder()
                .message("Deleted successfully")
                .build();
    }
}
