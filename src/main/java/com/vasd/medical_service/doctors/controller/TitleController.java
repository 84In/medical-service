package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateTitleDto;
import com.vasd.medical_service.doctors.dto.request.UpdateTitleDto;
import com.vasd.medical_service.doctors.dto.response.TitleResponseDto;
import com.vasd.medical_service.doctors.service.TitleService;
import com.vasd.medical_service.dto.PaginatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @GetMapping("/search")
    @Operation(
            summary = "Search titles with pagination",
            description = "Returns a paginated list of titles filtered by keyword. Keyword can match name, description and status.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (default is 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of records per page (default is 10)", example = "10"),
                    @Parameter(name = "keyword", description = "Search keyword (name, description, status)", example = "cardiology")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Paginated list of titles returned successfully")
            }
    )
    public ApiResponse<PaginatedResponse<TitleResponseDto>> getTitles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<TitleResponseDto> titleDtos = titleService.getAllTitles(pageable, keyword, status);
        return ApiResponse.<PaginatedResponse<TitleResponseDto>>builder()
                .result(new PaginatedResponse<>(titleDtos))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new title information", description = "Return the newly created title information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly created title information")
    public ApiResponse<TitleResponseDto> creatTitle(@RequestBody @Valid CreateTitleDto request) {

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
