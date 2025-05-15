package com.vasd.medical_service.auth.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    private String phone;
    private String email;
    private Long roleId;
}
