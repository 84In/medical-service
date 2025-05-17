package com.vasd.medical_service.medical_services.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateServiceTypeDto {

    private String name;
    private String description;
    private Integer status;
}
