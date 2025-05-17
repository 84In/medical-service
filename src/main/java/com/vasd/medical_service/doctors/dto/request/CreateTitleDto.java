package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateTitleDto {

    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Tên học hàm hoặc học vị của bác sĩ",
            example = "Gs"
    )
    private String name;

    @Schema(
            description = "Mô tả chi tiết về học hàm/học vị",
            example = "Giáo sư chuyên ngành tim mạch, có hơn 20 năm kinh nghiệm giảng dạy và nghiên cứu."
    )
    private String description;

    @NotNull
    @Schema(
            description = "Trạng thái của học hàm/học vị (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
