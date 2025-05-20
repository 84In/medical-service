package com.vasd.medical_service.news.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.news.dto.request.CreateNewsDto;
import com.vasd.medical_service.news.dto.request.UpdateNewsDto;
import com.vasd.medical_service.news.dto.response.NewsResponseDto;
import com.vasd.medical_service.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
