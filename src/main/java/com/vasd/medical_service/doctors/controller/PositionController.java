package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreatePositionDto;
import com.vasd.medical_service.doctors.dto.request.UpdatePositionDto;
import com.vasd.medical_service.doctors.dto.response.PositionResponseDto;
import com.vasd.medical_service.doctors.service.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/positions")
@RequiredArgsConstructor
@Tag(name = "Position Controller", description = "Doctor Position Management in the System (e.g. Director, Head of Department, etc.)")
public class PositionController {

    private final PositionService positionService;

    @GetMapping
    @Operation(summary = "Get list of position information", description = "Return the list of position information in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Position Information List")
    public ApiResponse<List<PositionResponseDto>> getPositions() {

        return ApiResponse.<List<PositionResponseDto>>builder()
                .result(positionService.getAllPositions())
                .build();
    }

    @GetMapping("/positionId")
    @Operation(summary = "Get position information by ID", description = "Return position information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Position Information")
    public ApiResponse<PositionResponseDto> getPosition(@PathVariable Long positionId) {
        return ApiResponse.<PositionResponseDto>builder()
                .result(positionService.getPosition(positionId))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new position information", description = "Return the newly created position information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created position information")
    public ApiResponse<PositionResponseDto> createPosition(@RequestBody CreatePositionDto request) {
        return ApiResponse.<PositionResponseDto>builder()
                .result(positionService.createPosition(request))
                .build();
    }

    @PutMapping("/{positionId}")
    @Operation(summary = "Update position information", description = "Return the recently updated information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated information")
    public ApiResponse<PositionResponseDto> updatePosition(@PathVariable Long positionId, @RequestBody UpdatePositionDto request) {

        return ApiResponse.<PositionResponseDto>builder()
                .result(positionService.updatePosition(positionId, request))
                .build();
    }

    @DeleteMapping("/{positionId}")
    @Operation(summary = "Delete position by ID", description = "Return successful deletion message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> deletePosition(@PathVariable Long positionId) {
        positionService.deletePosition(positionId);
        return ApiResponse.<Void>builder().message("Deleted successfully").build();
    }
}
