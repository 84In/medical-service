package com.vasd.medical_service.news.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.dto.PaginatedResponse;
import com.vasd.medical_service.news.dto.request.CreateNewsTypeDto;
import com.vasd.medical_service.news.dto.request.UpdateNewsTypeDto;
import com.vasd.medical_service.news.dto.response.NewsResponseDto;
import com.vasd.medical_service.news.dto.response.NewsTypeResponseDto;
import com.vasd.medical_service.news.service.NewsTypeService;
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
@RequestMapping("/api/v1/news-types")
@RequiredArgsConstructor
@Tag(name = "News Type Controller", description = "Manage news type services")
public class NewsTypeController {

    private final NewsTypeService newsTypeService;

    @GetMapping
    @Operation(summary = "Get list of all news type information", description = "Return the list of news type available in the system.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "News Type List")
    public ApiResponse<List<NewsTypeResponseDto>> getAllNewsTypes() {
        return ApiResponse.<List<NewsTypeResponseDto>>builder()
                .result(newsTypeService.getAllNewsTypes())
                .build();
    }

    @GetMapping("/{newsTypeId}")
    @Operation(summary = "Get news type information by ID", description = "Return news type information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "News type information")
    public ApiResponse<NewsTypeResponseDto> getNewsTypeById(@PathVariable Long newsTypeId) {

        return ApiResponse.<NewsTypeResponseDto>builder()
                .result(newsTypeService.getNewsTypeById(newsTypeId))
                .build();
    }
    @GetMapping("/search")
    @Operation(
            summary = "Search news type",
            description = "Returns a paginated list of news type, supporting optional keyword search by name and filtering by status (ACTIVE or INACTIVE).",
            parameters = {
                    @Parameter(name = "page", description = "Current page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10"),
                    @Parameter(name = "keyword", description = "Keyword to search by news type name", example = "internal medicine"),
                    @Parameter(name = "status", description = "Filter by news type status (ACTIVE or INACTIVE)", example = "ACTIVE"),
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of news type",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            }
    )
    public ApiResponse<PaginatedResponse<NewsTypeResponseDto>> searchNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by( "id"));
        Page<NewsTypeResponseDto> dtos = newsTypeService.getAllNewsTypes(pageable, keyword, status);
        return ApiResponse.<PaginatedResponse<NewsTypeResponseDto>>builder()
                .result(new PaginatedResponse<>(dtos))
                .build();
    }

    @PostMapping
    @Operation(summary = "Create new news type information", description = "Return the newly created news type information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly create news type information")
    public ApiResponse<NewsTypeResponseDto> createNewsType(@RequestBody CreateNewsTypeDto request) {

        return ApiResponse.<NewsTypeResponseDto>builder()
                .result(newsTypeService.createNewsType(request))
                .build();

    }

    @PutMapping("/{newsTypeId}")
    @Operation(summary = "Update news type information by ID", description = "Return the recently updated news type information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated news type information")
    public ApiResponse<NewsTypeResponseDto> updateNewsTypeById(@PathVariable Long newsTypeId, @RequestBody UpdateNewsTypeDto request) {
        return ApiResponse.<NewsTypeResponseDto>builder()
                .result(newsTypeService.updateNewsType(newsTypeId, request))
                .build();
    }

    @DeleteMapping("/{newsTypeId}")
    @Operation(summary = "Delete news type information by ID", description = "Return successful deletion message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",description = "Response message")
    public ApiResponse<Void> deleteNewsTypeById(@PathVariable Long newsTypeId) {
        newsTypeService.deleteNewsType(newsTypeId);
        return ApiResponse.<Void>builder()
                .message("Delete news type information")
                .build();
    }
}
