package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDoctorDto {

    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Tên bác sĩ",
            example = "BS. Trần Văn An"
    )
    private String name;

    @Schema(
            description = "URL ảnh đại diện của bác sĩ",
            example = "https://cdn.example.com/avatars/doctor-01.jpg"
    )
    private String avatarUrl;

    @Schema(
            description = "Giới thiệu ngắn gọn về bác sĩ",
            example = "Chuyên gia tim mạch với 15 năm kinh nghiệm điều trị bệnh mạch vành"
    )
    private String introduction;

    @Pattern(regexp = "^[0-9]+$")
    @Schema(
            description = "Số năm kinh nghiệm",
            example = "15"
    )
    private String experienceYears;

    @NotNull
    @Schema(
            description = "ID khoa chuyên môn mà bác sĩ trực thuộc",
            example = "3"
    )
    private Long departmentId;

    @NotNull
    @Schema(
            description = "ID chức vụ (position) của bác sĩ",
            example = "2"
    )
    private Long positionId;

    @NotNull
    @Schema(
            description = "ID học hàm/học vị (title) của bác sĩ",
            example = "1"
    )
    private Long titleId;

    @NotNull
    @Schema(
            description = "Trạng thái của bác sĩ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
