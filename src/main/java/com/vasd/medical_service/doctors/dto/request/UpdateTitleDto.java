package com.vasd.medical_service.doctors.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTitleDto {
    private String name;
    private String description;
    private Integer status;
}
