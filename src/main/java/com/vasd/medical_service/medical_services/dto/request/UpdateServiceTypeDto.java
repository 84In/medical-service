package com.vasd.medical_service.medical_services.dto.request;

import com.vasd.medical_service.Enum.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateServiceTypeDto {

    private String name;
    private String description;
    private Status status;
}
