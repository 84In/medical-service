package com.vasd.medical_service.auth;

import com.vasd.medical_service.auth.dto.ChangePasswordUserDto;
import com.vasd.medical_service.auth.dto.CreateUserDto;
import com.vasd.medical_service.auth.dto.ResponseUserDto;
import com.vasd.medical_service.auth.dto.UpdateUserDto;
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
    public ApiResponse<List<ResponseUserDto>> getUsers() {
        List<ResponseUserDto> users = userService.getAllUsers();
        return ApiResponse.<List<ResponseUserDto>>builder().result(users).build();
    }

    @PostMapping()
    public ApiResponse<ResponseUserDto> createUser(@RequestBody CreateUserDto request) {
        ResponseUserDto user = userService.createUser(request);
        return ApiResponse.<ResponseUserDto>builder().result(user).build();
    }

    @PutMapping("/updateInfo/{userId}")
    public ApiResponse<ResponseUserDto> updateInfoUser(@PathVariable Long userId, @RequestBody UpdateUserDto request) {
        ResponseUserDto user = userService.updateUser(request, userId);
        return ApiResponse.<ResponseUserDto>builder().result(user).build();
    }

    @PutMapping("/changePassword/{userId}")
    public ApiResponse<ResponseUserDto> changePassword(@PathVariable Long userId, @RequestBody ChangePasswordUserDto request) {
        ResponseUserDto user = userService.changePassword(request, userId);
        return ApiResponse.<ResponseUserDto>builder().result(user).build();
    }

    @DeleteMapping("/{userId}")
    public ApiResponse<ResponseUserDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<ResponseUserDto>builder().message("User is deleted!").build();
    }


}
