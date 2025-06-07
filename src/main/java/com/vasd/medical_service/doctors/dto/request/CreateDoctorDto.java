package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.dto.AchievementDTO;
import com.vasd.medical_service.doctors.dto.EducationDTO;
import com.vasd.medical_service.doctors.dto.ExperienceDTO;
import com.vasd.medical_service.doctors.dto.WorkingHourDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

    @Pattern(
            regexp = "(\\+?\\d{1,3}[\\s-]?)?(\\(\\d{3}\\)|\\d{3})[\\s-]?\\d{3}[\\s-]?\\d{3,4}|\\d{11}",
            message = "Không đúng định dạng số điện thoại"
    )
    @Schema(
            description = "Số điện thoại của bác sĩ, hỗ trợ các định dạng: " +
                    "11 chữ số liên tiếp (01234567890), " +
                    "dạng 3 số - 3 số - 4 số (012-345-6789), " +
                    "dạng (xxx)xxx-xxxx ((012)345-6789), " +
                    "dạng quốc tế với dấu + và khoảng trắng hoặc dấu - (+84 776 872 151, +1-123-456-7890).",
            example = "+84 776 872 151"
    )
    private String phone;


    @Email
    @Schema( description = "Địa chỉ Email của bác sĩ", example = "thhai@vitacare.com")
    private String email;

    private List<Long> specialtyIds;

    private List<EducationDTO> education;

    private List<ExperienceDTO> workExperience;

    private List<AchievementDTO> achievements;

    private List<WorkingHourDTO> workingHours;
}
