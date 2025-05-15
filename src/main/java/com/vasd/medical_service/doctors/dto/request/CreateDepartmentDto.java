package com.vasd.medical_service.doctors.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDepartmentDto {

    private String name;
    private String contentHtml;
    private Integer status;
}
