package com.vasd.medical_service.news.controller;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.dto.PaginatedResponse;
import com.vasd.medical_service.medical_services.dto.response.ServiceResponseDto;
import com.vasd.medical_service.news.dto.request.CreateNewsDto;
import com.vasd.medical_service.news.dto.request.UpdateNewsDto;
import com.vasd.medical_service.news.dto.response.NewsResponseDto;
import com.vasd.medical_service.news.service.NewsService;
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
@RequestMapping("/api/v1/news")
@RequiredArgsConstructor
@Tag(name = "News Controller", description = "Manage news service")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Get list of news information", description = "Return the list of news available in the system.")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "News List")
    public ApiResponse<List<NewsResponseDto>> getNews() {
        return ApiResponse.<List<NewsResponseDto>>builder()
                .result(newsService.getAllNews())
                .build();
    }

    @GetMapping("/{newsId}")
    @Operation(summary = "Get news information by ID", description = "Return news information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "News information")
    public ApiResponse<NewsResponseDto> getNews(@PathVariable("newsId") Long newsId) {
        return ApiResponse.<NewsResponseDto>builder()
                .result(newsService.getNewsById(newsId))
                .build();
    }
    @GetMapping("slug/{slug}")
    @Operation(summary = "Get news information by ID", description = "Return news information by ID")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "News information")
    public ApiResponse<NewsResponseDto> getNewsBySlug(@PathVariable("slug") String slug) {
        return ApiResponse.<NewsResponseDto>builder()
                .result(newsService.getNewsBySlug(slug))
                .build();
    }
    @GetMapping("/search")
    @Operation(
            summary = "Search news",
            description = "Returns a paginated list of news, supporting optional keyword search by name and filtering by status (ACTIVE or INACTIVE).",
            parameters = {
                    @Parameter(name = "page", description = "Current page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of items per page", example = "10"),
                    @Parameter(name = "keyword", description = "Keyword to search by news name", example = "internal medicine"),
                    @Parameter(name = "status", description = "Filter by news status (ACTIVE or INACTIVE)", example = "ACTIVE"),
                    @Parameter(name= "newsTypeId", description = "News type id to find news contain news type id", example = "1")
            },
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "200",
                            description = "Paginated list of news",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PaginatedResponse.class)
                            )
                    )
            }
    )
    public ApiResponse<PaginatedResponse<NewsResponseDto>> searchNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Long newsTypeId
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<NewsResponseDto> dtos = newsService.getAllNews(pageable, keyword, status, newsTypeId);
        return ApiResponse.<PaginatedResponse<NewsResponseDto>>builder()
                .result(new PaginatedResponse<>(dtos))
                .build();
    }


    @PostMapping
    @Operation(summary = "Create new news information", description = "Return the newly created news information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Newly create news information")
    public ApiResponse<NewsResponseDto> createNews(@RequestBody CreateNewsDto request) {
        return ApiResponse.<NewsResponseDto>builder()
                .result(newsService.createNews(request))
                .build();
    }

    @PutMapping("/{newsId}")
    @Operation(summary = "Update news information by ID", description = "Return the recently updated news information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated news information")
    public ApiResponse<NewsResponseDto> updateNews(@PathVariable Long newsId, @RequestBody UpdateNewsDto request) {
        return ApiResponse.<NewsResponseDto>builder()
                .result(newsService.updateNews(newsId, request))
                .build();
    }

    @DeleteMapping("/{newsId}")
    @Operation(summary = "Delete news information by ID", description = "Return successful deletion message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",description = "Response message")
    public ApiResponse<Void> deleteNews(@PathVariable("newsId") Long newsId) {
        newsService.deleteNews(newsId);
        return ApiResponse.<Void>builder()
                .message("Deleted successfully")
                .build();
    }
}
