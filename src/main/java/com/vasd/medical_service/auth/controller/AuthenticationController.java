package com.vasd.medical_service.auth.controller;

import com.vasd.medical_service.auth.dto.request.LoginRequestDto;
import com.vasd.medical_service.auth.dto.response.AuthenticationResponseDto;
import com.vasd.medical_service.auth.dto.request.LogoutRequestDto;
import com.vasd.medical_service.auth.service.AuthenticationService;
import com.vasd.medical_service.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponseDto> login(@RequestBody LoginRequestDto request) {
        return ApiResponse.<AuthenticationResponseDto>builder()
                .result(authenticationService.authenticate(request)).build();
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequestDto request) {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().message("Logout success!").build();
    }
}
