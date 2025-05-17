package com.vasd.medical_service.doctors.dto.response;

import com.vasd.medical_service.Enum.Status;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PositionResponseDto {

    private Long id;
    private String name;
    private String description;
    private Status status;
}
