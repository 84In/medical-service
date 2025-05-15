package com.vasd.medical_service.doctors.controller;

import com.vasd.medical_service.common.ApiResponse;
import com.vasd.medical_service.doctors.dto.request.CreateTitleDto;
import com.vasd.medical_service.doctors.dto.request.UpdateTitleDto;
import com.vasd.medical_service.doctors.dto.response.TitleResponseDto;
import com.vasd.medical_service.doctors.service.TitleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/titles")
@RequiredArgsConstructor
public class TitleController {

    private final TitleService titleService;

    @GetMapping
    public ApiResponse<List<TitleResponseDto>> getTitles() {

        return ApiResponse.<List<TitleResponseDto>>builder()
                .result(titleService.getAllTitles())
                .build();
    }

    @GetMapping("/{titleId}")
    public ApiResponse<TitleResponseDto> getTitle(@PathVariable Long titleId) {

        return ApiResponse.<TitleResponseDto>builder()
                .result(titleService.getTitleById(titleId))
                .build();
    }

    @PostMapping
    public ApiResponse<TitleResponseDto> creatTitle(@RequestBody CreateTitleDto request) {

        return ApiResponse.<TitleResponseDto>builder()
                .result(titleService.creatTitle(request))
                .build();
    }

    @PutMapping("/{titleId}")
    public ApiResponse<TitleResponseDto> updateTitle(@PathVariable Long titleId, @RequestBody UpdateTitleDto request) {

        return ApiResponse.<TitleResponseDto>builder()
                .result(titleService.updateTitle(titleId, request))
                .build();
    }

    @DeleteMapping("/{titleId}")
    public ApiResponse<Void> deleteTitle(@PathVariable Long titleId) {

        return ApiResponse.<Void>builder()
                .message("Title is delete success!")
                .build();
    }
}
