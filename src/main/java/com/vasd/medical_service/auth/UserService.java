package com.vasd.medical_service.auth;

import com.vasd.medical_service.auth.dto.*;
import com.vasd.medical_service.auth.model.Role;
import com.vasd.medical_service.auth.model.RoleRepository;
import com.vasd.medical_service.auth.model.User;
import com.vasd.medical_service.auth.model.UserRepository;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseUserDto createUser(CreateUserDto model) {
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

    public List<ResponseUserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<ResponseUserDto> userDtos = new ArrayList<>();

        users.forEach(user -> userDtos.add(mapUsertoUserDto(user)));

        log.info("Get all users");
        return userDtos;
    }

    public ResponseUserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        log.info("Get user by id {}", user.getId());
        return mapUsertoUserDto(user);
    }

    public ResponseUserDto getUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        log.info("Get user by username {}", user.getUsername());
        return mapUsertoUserDto(user);
    }

    public ResponseUserDto updateUser(UpdateUserDto model, Long id) {
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

    public ResponseUserDto changePassword(ChangePasswordUserDto model, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if(!checkPassword(model.getPassword(), user)) {
            log.info("Invalid Password from user {}", user.getUsername());
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(model.getNewPassword()));
        log.info("Password updated {}", user.getPassword());
        return mapUsertoUserDto(user);
    }

    public void deleteUser(Long id) {
        log.info("User deleted id {}", id);
        userRepository.deleteById(id);
    }

    private ResponseUserDto mapUsertoUserDto(User user) {
        ResponseRoleDto responseRoleDto = ResponseRoleDto.builder()
                .id(user.getRole().getId())
                .name(user.getRole().getName())
                .build();
        return ResponseUserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(responseRoleDto)
                .build();
    }
    private boolean checkPassword(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }
}
