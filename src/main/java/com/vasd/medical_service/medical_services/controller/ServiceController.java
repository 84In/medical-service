package com.vasd.medical_service.medical_services.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.dto.PaginatedResponse;
import com.vasd.medical_service.medical_services.dto.request.CreateServiceDto;
import com.vasd.medical_service.medical_services.dto.request.UpdateServiceDto;
import com.vasd.medical_service.medical_services.dto.response.ServiceResponseDto;
import com.vasd.medical_service.medical_services.service.ServiceService;
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
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
@Tag(name = "Service controller", description = "Manage healthcare services")
public class ServiceController {

    private final ServiceService serviceService;

    @GetMapping()
    @Operation(summary = "Get list of all service information", description = "Return the list of services available in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Service List")
    public ApiResponse<List<ServiceResponseDto>> getServices() {

        return ApiResponse.<List<ServiceResponseDto>>builder()
                .result(serviceService.getAllServices())
                .build();
    }

    @GetMapping("/{serviceId}")
    @Operation(summary = "Get service information by ID", description = "Return service information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Service information")
    public ApiResponse<ServiceResponseDto> getServiceById(@PathVariable Long serviceId) {

        return ApiResponse.<ServiceResponseDto>builder()
                .result(serviceService.getServiceById(serviceId))
                .build();
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search services",
            description = "Returns a paginated list of services, supporting optional keyword search by name and filtering by status (ACTIVE or INACTIVE).",
            parameters = {
                    @Parameter(name = "page", description = "Current page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10"),
                    @Parameter(name = "keyword", description = "Keyword to search by service name", example = "internal medicine"),
                    @Parameter(name = "status", description = "Filter by service status (ACTIVE or INACTIVE)", example = "ACTIVE"),
                    @Parameter(name= "serviceTypeId", description = "Service type id to find service contain service type id", example = "1")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of services",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            }
    )
    public ApiResponse<PaginatedResponse<ServiceResponseDto>> searchServices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long serviceTypeId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<ServiceResponseDto> dtos = serviceService.getAllServices(pageable, keyword, status, serviceTypeId);
        return ApiResponse.<PaginatedResponse<ServiceResponseDto>>builder()
                .result(new PaginatedResponse<>(dtos))
                .build();
    }


    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get service information by slug", description = "Return service information by slug")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Service information")
    public ApiResponse<ServiceResponseDto> getServiceBySlug(@PathVariable String slug) {
        return ApiResponse.<ServiceResponseDto>builder()
                .result(serviceService.getServiceBySlug(slug))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new service information", description = "Return the newly created service information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created service information")
    public ApiResponse<ServiceResponseDto> createService(@RequestBody CreateServiceDto request){

        return ApiResponse.<ServiceResponseDto>builder()
                .result(serviceService.createService(request))
                .build();
    }

    @PutMapping("/{serviceId}")
    @Operation(summary = "Update service information by ID", description = "Return recently updated service information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated service information by ID")
    public ApiResponse<ServiceResponseDto> updateServiceById(@PathVariable Long serviceId, @RequestBody UpdateServiceDto request){
        return ApiResponse.<ServiceResponseDto>builder()
                .result(serviceService.updateService(serviceId, request))
                .build();
    }

    @DeleteMapping("/{serviceId}")
    @Operation(summary = "Delete service information by ID", description = "Return successful deletion status")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> deleteServiceById(@PathVariable Long serviceId) {
        serviceService.deleteServiceById(serviceId);
        return ApiResponse.<Void>builder()
                .message("Service deleted successfully")
                .build();
    }


}
