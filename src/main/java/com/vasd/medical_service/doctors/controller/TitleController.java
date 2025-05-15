package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateTitleDto;
import com.vasd.medical_service.doctors.dto.request.UpdateTitleDto;
import com.vasd.medical_service.doctors.dto.response.TitleResponseDto;
import com.vasd.medical_service.doctors.service.TitleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/titles")
@RequiredArgsConstructor
@Tag(name = "Title Controller", description = "Doctor Title Management")
public class TitleController {

    private final TitleService titleService;

    @GetMapping
    @Operation(summary = "Get list of all title information", description = "Return the list of titles available in the system")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Title List")
    public ApiResponse<List<TitleResponseDto>> getTitles() {

        return ApiResponse.<List<TitleResponseDto>>builder()
                .result(titleService.getAllTitles())
                .build();
    }

    @GetMapping("/{titleId}")
    @Operation(summary = "Get title information by ID", description = "Return title information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Title Information")
    public ApiResponse<TitleResponseDto> getTitle(@PathVariable Long titleId) {

        return ApiResponse.<TitleResponseDto>builder()
                .result(titleService.getTitleById(titleId))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new title information", description = "Return the newly created title information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created title information")
    public ApiResponse<TitleResponseDto> creatTitle(@RequestBody CreateTitleDto request) {

        return ApiResponse.<TitleResponseDto>builder()
                .result(titleService.creatTitle(request))
                .build();
    }

    @PutMapping("/{titleId}")
    @Operation(summary = "Update title information by ID", description = "Return the recently updated title information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",description = "Recently updated title information")
    public ApiResponse<TitleResponseDto> updateTitle(@PathVariable Long titleId, @RequestBody UpdateTitleDto request) {

        return ApiResponse.<TitleResponseDto>builder()
                .result(titleService.updateTitle(titleId, request))
                .build();
    }

    @DeleteMapping("/{titleId}")
    @Operation(summary = "Delete title information by ID", description = "Return successful deletion status")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> deleteTitle(@PathVariable Long titleId) {

        titleService.deleteTitle(titleId);
        return ApiResponse.<Void>builder()
                .message("Deleted successfully")
                .build();
    }
}
