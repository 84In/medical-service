package com.vasd.medical_service.doctors.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreatePositionDto {

    private String name;
    private String description;
    private Integer status;
}
