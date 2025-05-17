package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDepartmentDto {

    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Tên khoa chuyên môn",
            example = "Khoa Tim mạch"
    )
    private String name;

    @Schema(
            description = "Nội dung chi tiết (HTML) của khoa chuyên môn",
            example = "<h2>Khoa Tim mạch</h2><p>Đội ngũ y bác sĩ nhiều kinh nghiệm...</p>"
    )
    private String contentHtml;

    @NotNull
    @Schema(
            description = "Trạng thái của khoa chuyên môn (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
