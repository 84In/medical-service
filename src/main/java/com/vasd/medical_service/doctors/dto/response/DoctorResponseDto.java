package com.vasd.medical_service.doctors.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorResponseDto {

    private Long id;
    private String name;
    private String avatarUrl;
    private String introduction;
    private String experience_years;
    private Integer status;
    private DepartmentResponseDto department;
    private PositionResponseDto position;
    private TitleResponseDto title;
}
