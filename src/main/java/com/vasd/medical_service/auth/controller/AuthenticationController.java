package com.vasd.medical_service.auth.controller;

import com.vasd.medical_service.auth.dto.request.LoginRequestDto;
import com.vasd.medical_service.auth.dto.response.AuthenticationResponseDto;
import com.vasd.medical_service.auth.dto.request.LogoutRequestDto;
import com.vasd.medical_service.auth.service.AuthenticationService;
import com.vasd.medical_service.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "API endpoints for user login and logout operations")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticate user and return access token")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",  description = "Successful login response")
    public ApiResponse<AuthenticationResponseDto> login(@RequestBody LoginRequestDto request) {
        return ApiResponse.<AuthenticationResponseDto>builder()
                .result(authenticationService.authenticate(request)).build();
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Remove token and logout")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Successful logout response")
    public ApiResponse<Void> logout(@RequestBody LogoutRequestDto request) {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().message("Logout success!").build();
    }
}
