package com.vasd.medical_service.medical_services.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.medical_services.dto.request.CreateServiceDto;
import com.vasd.medical_service.medical_services.dto.request.UpdateServiceDto;
import com.vasd.medical_service.medical_services.dto.response.ServiceResponseDto;
import com.vasd.medical_service.medical_services.service.ServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
                .result(serviceService.findAllServices())
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

    @GetMapping("/{slug}")
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
