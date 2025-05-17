package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePositionDto {

    @Schema(description = "Tên chức vụ (null nếu không cập nhật)", example = "Trưởng khoa")
    private String name;

    @Schema(description = "Mô tả chi tiết về chức vụ (null nếu không cập nhật)", example = "Quản lý khoa và điều hành công tác chuyên môn")
    private String description;

    @Schema(
            description = "Trạng thái chức vụ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3) (null nếu không cập nhật)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
