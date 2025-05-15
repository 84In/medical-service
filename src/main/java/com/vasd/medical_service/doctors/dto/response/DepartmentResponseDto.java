package com.vasd.medical_service.doctors.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentResponseDto {

    private Long id;

    private String name;

    private String contentHtml;

    private Integer status;
}
