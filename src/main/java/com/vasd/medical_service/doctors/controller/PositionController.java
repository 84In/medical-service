package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreatePositionDto;
import com.vasd.medical_service.doctors.dto.request.UpdatePositionDto;
import com.vasd.medical_service.doctors.dto.response.PositionResponseDto;
import com.vasd.medical_service.doctors.service.PositionService;
import com.vasd.medical_service.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @GetMapping("/search")
    @Operation(
            summary = "Search positions with pagination",
            description = "Returns a paginated list of positions filtered by keyword. Keyword can match name, description and status.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (default is 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of records per page (default is 10)", example = "10"),
                    @Parameter(name = "keyword", description = "Search keyword (name, description, status)", example = "cardiology")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Paginated list of positions returned successfully")
            }
    )
    public ApiResponse<PaginatedResponse<PositionResponseDto>> getpositions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<PositionResponseDto> positionDtos = positionService.getAllPositions(pageable, keyword, status);
        return ApiResponse.<PaginatedResponse<PositionResponseDto>>builder()
                .result(new PaginatedResponse<>(positionDtos))
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
    public ApiResponse<PositionResponseDto> createPosition(@RequestBody @Valid CreatePositionDto request) {
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
