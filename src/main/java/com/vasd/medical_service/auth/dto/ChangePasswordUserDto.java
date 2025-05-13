package com.vasd.medical_service.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordUserDto {
    private String password;
    private String newPassword;
}
