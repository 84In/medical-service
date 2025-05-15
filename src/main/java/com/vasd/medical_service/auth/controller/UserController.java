package com.vasd.medical_service.auth.controller;

import com.vasd.medical_service.auth.dto.request.UpdateUserDto;
import com.vasd.medical_service.auth.dto.request.ChangePasswordUserDto;
import com.vasd.medical_service.auth.dto.request.CreateUserDto;
import com.vasd.medical_service.auth.dto.response.UserResponseDto;
import com.vasd.medical_service.auth.service.UserService;
import com.vasd.medical_service.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ApiResponse<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ApiResponse.<List<UserResponseDto>>builder().result(users).build();
    }

    @PostMapping()
    public ApiResponse<UserResponseDto> createUser(@RequestBody CreateUserDto request) {
        UserResponseDto user = userService.createUser(request);
        return ApiResponse.<UserResponseDto>builder().result(user).build();
    }

    @PutMapping("/updateInfo/{userId}")
    public ApiResponse<UserResponseDto> updateInfoUser(@PathVariable Long userId, @RequestBody UpdateUserDto request) {
        UserResponseDto user = userService.updateUser(request, userId);
        return ApiResponse.<UserResponseDto>builder().result(user).build();
    }

    @PutMapping("/changePassword/{userId}")
    public ApiResponse<UserResponseDto> changePassword(@PathVariable Long userId, @RequestBody ChangePasswordUserDto request) {
        UserResponseDto user = userService.changePassword(request, userId);
        return ApiResponse.<UserResponseDto>builder().result(user).build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<UserResponseDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<UserResponseDto>builder().message("User is deleted!").build();
    }


}
