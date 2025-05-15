package com.vasd.medical_service.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private Long id;

    private String username;
    private String password;
    private String phone;
    private String email;

    private RoleResponseDto role;
}
