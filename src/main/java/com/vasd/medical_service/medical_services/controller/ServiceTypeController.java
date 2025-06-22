package com.vasd.medical_service.medical_services.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.response.DepartmentResponseDto;
import com.vasd.medical_service.dto.PaginatedResponse;
import com.vasd.medical_service.medical_services.dto.request.CreateServiceTypeDto;
import com.vasd.medical_service.medical_services.dto.request.UpdateServiceTypeDto;
import com.vasd.medical_service.medical_services.dto.response.ServiceTypeResponseDto;
import com.vasd.medical_service.medical_services.service.ServiceTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service-types")
@RequiredArgsConstructor
@Tag(name = "Service Type Controller", description = "Manage medical service types")
public class ServiceTypeController {

    private final ServiceTypeService serviceTypeService;

    @GetMapping
    @Operation(summary = "Get list of all service types information", description = "Return the list of service types available in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Service Type List")
    public ApiResponse<List<ServiceTypeResponseDto>> getServiceTypes() {

        return ApiResponse.<List<ServiceTypeResponseDto>>builder()
                .result(serviceTypeService.getAllServiceTypes())
                .build();
    }

    @GetMapping("/{serviceTypeId}")
    @Operation(summary = "Get service type information by ID", description = "Return service type information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Serivce Type Information")
    public ApiResponse<ServiceTypeResponseDto> getServiceTypeById(@PathVariable Long serviceTypeId) {
        return ApiResponse.<ServiceTypeResponseDto>builder()
                .result(serviceTypeService.getServiceTypeById(serviceTypeId))
                .build();
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search service types",
            description = "Returns a paginated list of service types, supporting optional keyword search by name and filtering by status (ACTIVE or INACTIVE).",
            parameters = {
                    @Parameter(name = "page", description = "Current page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10"),
                    @Parameter(name = "keyword", description = "Keyword to search by service type name", example = "internal medicine"),
                    @Parameter(name = "status", description = "Filter by service type status (ACTIVE or INACTIVE)", example = "ACTIVE")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of service types",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            }
    )
    public ApiResponse<PaginatedResponse<ServiceTypeResponseDto>> searchServiceTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<ServiceTypeResponseDto> dtos = serviceTypeService.getAllServiceTypes(keyword, status, pageable);
        return ApiResponse.<PaginatedResponse<ServiceTypeResponseDto>>builder()
                .result(new PaginatedResponse<>(dtos))
                .build();
    }


    @PostMapping
    @Operation(summary = "Create new service type information", description = "Return the newly created service type information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created service type information")
    public ApiResponse<ServiceTypeResponseDto> createServiceType(@RequestBody CreateServiceTypeDto request){

        return ApiResponse.<ServiceTypeResponseDto>builder()
                .result(serviceTypeService.createServiceType(request))
                .build();
    }

    @PutMapping("/{serviceTypeId}")
    @Operation(summary = "Update service type information by ID", description = "Return the recently updated service type information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",description = "Recently updated service type information")
    public ApiResponse<ServiceTypeResponseDto> updateServiceTypeById(@PathVariable Long serviceTypeId, @RequestBody UpdateServiceTypeDto request){

        return ApiResponse.<ServiceTypeResponseDto>builder()
                .result(serviceTypeService.updateServiceType(serviceTypeId, request))
                .build();
    }

    @DeleteMapping("/{serviceTypeId}")
    @Operation(summary = "Delete service type information by ID", description = "Return successful deletion status")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> deleteServiceTypeById(@PathVariable Long serviceTypeId) {
        serviceTypeService.deleteServiceType(serviceTypeId);
        return ApiResponse.<Void>builder()
                .message("Service Type Deleted Successfully")
                .build();
    }
}
