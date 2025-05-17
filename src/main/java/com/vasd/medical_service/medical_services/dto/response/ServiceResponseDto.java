package com.vasd.medical_service.medical_services.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ServiceResponseDto {
    private Long id;

    private String slug;
    private String name;
    private String thumbnailUrl;
    private String descriptionShort;
    private String contentHtml;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ServiceTypeResponseDto serviceType;
}
