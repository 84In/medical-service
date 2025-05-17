package com.vasd.medical_service.medical_services.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateServiceDto {

    private String slug;
    private String name;
    private String thumbnailUrl;
    private String descriptionShort;
    private String contentHtml;
    private Integer status;

    private Long serviceTypeId;
}
