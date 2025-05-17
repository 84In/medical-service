package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTitleDto {

    @Schema(description = "Tên học hàm/học vị (null nếu không cập nhật)", example = "Giáo sư")
    private String name;

    @Schema(description = "Mô tả chi tiết về học hàm/học vị (null nếu không cập nhật)", example = "Chuyên gia đầu ngành trong lĩnh vực tim mạch")
    private String description;

    @Schema(
            description = "Trạng thái học hàm/học vị (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3) (null nếu không cập nhật)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
