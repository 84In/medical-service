package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateDepartmentDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDepartmentDto;
import com.vasd.medical_service.doctors.dto.response.DepartmentResponseDto;
import com.vasd.medical_service.doctors.service.DepartmentService;
import com.vasd.medical_service.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/search")
    @Operation(
            summary = "Search departments",
            description = "Returns a paginated list of departments, supporting optional keyword search by name and filtering by status (ACTIVE or INACTIVE).",
            parameters = {
                    @Parameter(name = "page", description = "Current page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10"),
                    @Parameter(name = "keyword", description = "Keyword to search by department name", example = "internal medicine"),
                    @Parameter(name = "status", description = "Filter by department status (ACTIVE or INACTIVE)", example = "ACTIVE")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of departments",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            }
    )
    public ApiResponse<PaginatedResponse<DepartmentResponseDto>> searchDepartments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<DepartmentResponseDto> departmentDtos = departmentService.getAllDepartments(pageable, keyword, status);
        return ApiResponse.<PaginatedResponse<DepartmentResponseDto>>builder()
                .result(new PaginatedResponse<>(departmentDtos))
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
