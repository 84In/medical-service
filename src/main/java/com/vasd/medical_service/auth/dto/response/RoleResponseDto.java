package com.vasd.medical_service.auth.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponseDto {
    private Long id;
    private String name;
}
