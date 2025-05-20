package com.vasd.medical_service.news.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateNewsTypeDto {

    @Schema(description = "Tên của loại tin tức", example = "Tin tức y khoa")
    private String name;
    @Schema(
            description = "Trạng thái của dịch vụ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
