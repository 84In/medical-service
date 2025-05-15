package com.vasd.medical_service.auth.controller;

import com.vasd.medical_service.auth.dto.request.UpdateUserDto;
import com.vasd.medical_service.auth.dto.request.ChangePasswordUserDto;
import com.vasd.medical_service.auth.dto.request.CreateUserDto;
import com.vasd.medical_service.auth.dto.response.UserResponseDto;
import com.vasd.medical_service.auth.service.UserService;
import com.vasd.medical_service.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Management")
public class UserController {

    private final UserService userService;

    @GetMapping()
    @Operation(summary = "Fetch list of users", description = "Return the list of users (with password field set to null)")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0",description = "User List")
    public ApiResponse<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ApiResponse.<List<UserResponseDto>>builder().result(users).build();
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get user information by ID", description = "Return User Information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "User Information")
    public ApiResponse<UserResponseDto> getUser(@PathVariable String username) {
        return ApiResponse.<UserResponseDto>builder()
                .result(userService.getUserByUsername(username))
                .build();
    }

    @PostMapping()
    @Operation(summary = "Create new user information", description = "Return the newly created user information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "New User Information")
    public ApiResponse<UserResponseDto> createUser(@RequestBody CreateUserDto request) {
        UserResponseDto user = userService.createUser(request);
        return ApiResponse.<UserResponseDto>builder().result(user).build();
    }

    @PutMapping("/updateInfo/{userId}")
    @Operation(summary = "Update user information by ID", description = "Return the recently updated user information")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Recently updated user information")
    public ApiResponse<UserResponseDto> updateInfoUser(@PathVariable Long userId, @RequestBody UpdateUserDto request) {
        UserResponseDto user = userService.updateUser(request, userId);
        return ApiResponse.<UserResponseDto>builder().result(user).build();
    }

    @PutMapping("/changePassword/{userId}")
    @Operation(summary = "Change password", description = "Return successful message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<Void> changePassword(@PathVariable Long userId, @RequestBody ChangePasswordUserDto request) {
        return ApiResponse.<Void>builder()
                .message(userService.changePassword(request, userId))
                .build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user information by ID", description = "Return successful deletion message")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "0", description = "Response message")
    public ApiResponse<UserResponseDto> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ApiResponse.<UserResponseDto>builder().message("User is deleted!").build();
    }


}
