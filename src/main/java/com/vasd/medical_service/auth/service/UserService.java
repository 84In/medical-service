package com.vasd.medical_service.auth.service;

import com.vasd.medical_service.auth.dto.request.ChangePasswordUserDto;
import com.vasd.medical_service.auth.dto.request.CreateUserDto;
import com.vasd.medical_service.auth.dto.request.UpdateUserDto;
import com.vasd.medical_service.auth.dto.response.RoleResponseDto;
import com.vasd.medical_service.auth.dto.response.UserResponseDto;
import com.vasd.medical_service.auth.entities.Role;
import com.vasd.medical_service.auth.entities.User;
import com.vasd.medical_service.auth.repository.RoleRepository;
import com.vasd.medical_service.auth.repository.UserRepository;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(CreateUserDto model) {
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        user.setEmail(model.getEmail());
        user.setPhone(model.getPhone());
        Role role = roleRepository.findById(model.getRoleId()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
        user.setRole(role);
        User userResult = userRepository.save(user);
        log.info("User created id {} and username {}", userResult.getId(), user.getUsername());
        return mapUsertoUserDto(userResult);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Get all users");
        return users.stream().map(this::mapUsertoUserDto).collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        log.info("Get user by id {}", user.getId());
        return mapUsertoUserDto(user);
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        log.info("Get user by username {}", user.getUsername());
        return mapUsertoUserDto(user);
    }

    public UserResponseDto updateUser(UpdateUserDto model, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(model.getEmail() != null) {
            user.setEmail(model.getEmail());
            log.info("Email updated {}", user.getEmail());
        }
        if(model.getPhone() != null) {
            user.setPhone(model.getPhone());
            log.info("Phone updated {}", user.getPhone());
        }
        if(model.getRoleId() != null) {
            Role role = roleRepository.findById(model.getRoleId()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));
            user.setRole(role);
            log.info("Role updated {}", user.getRole());
        }
        log.info("User updated id {}", user.getId());
        return mapUsertoUserDto(user);
    }

    public String changePassword(ChangePasswordUserDto model, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!checkPassword(model.getPassword(), user)) {
            log.info("Invalid Password from user {}", user.getUsername());
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(model.getNewPassword()));
        log.info("Password updated {}", user.getPassword());
        userRepository.save(user);
        return "Password updated successfully";
    }

    public void deleteUser(Long id) {
        log.info("User deleted id {}", id);
        userRepository.deleteById(id);
    }

    private UserResponseDto mapUsertoUserDto(User user) {
        RoleResponseDto roleResponseDto = RoleResponseDto.builder()
                .id(user.getRole().getId())
                .name(user.getRole().getName())
                .build();
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(roleResponseDto)
                .build();
    }
    private boolean checkPassword(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
