package com.vasd.medical_service.doctors.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDoctorDto {

    private String name;

    private String avatarUrl;

    private String introduction;

    private String experience_years;

    private Long department_id;

    private Long position_id;

    private Long title_id;

    private Integer status;
}
