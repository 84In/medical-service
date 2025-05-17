package com.vasd.medical_service.medical_services.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateServiceTypeDto {


    @Schema(
            description = "Tên loại dịch vụ y tế",
            example = "Dịch vụ nội khoa"
    )
    private String name;

    @Schema(
            description = "Mô tả chi tiết về loại dịch vụ",
            example = "Các dịch vụ liên quan đến khám và điều trị các bệnh nội khoa"
    )
    private String description;

    @Schema(
            description = "Trạng thái của loại dịch vụ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
