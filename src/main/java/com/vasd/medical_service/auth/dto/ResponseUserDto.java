package com.vasd.medical_service.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserDto {

    private Long id;

    private String username;
    private String password;
    private String phone;
    private String email;

    private ResponseRoleDto role;
}
