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
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class UpdateDoctorDto {

    @NotNull
    @Schema(description = "ID bác sĩ cần cập nhật", example = "5")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "Tên bác sĩ", example = "BS. Nguyễn Văn B")
    private String name;

    @Schema(description = "URL ảnh đại diện của bác sĩ", example = "https://cdn.example.com/avatars/doctor-05.jpg")
    private String avatarUrl;

    @Schema(description = "Giới thiệu ngắn gọn về bác sĩ", example = "Bác sĩ nội tiết với hơn 10 năm kinh nghiệm điều trị tiểu đường")
    private String introduction;

    @Pattern(regexp = "^[0-9]+$")
    @Schema(description = "Số năm kinh nghiệm", example = "10")
    private String experienceYears;

    @NotNull
    @Schema(description = "ID khoa chuyên môn mà bác sĩ trực thuộc", example = "2")
    private Long departmentId;

    @NotNull
    @Schema(description = "ID chức vụ (position) của bác sĩ", example = "1")
    private Long positionId;

    @NotNull
    @Schema(description = "ID học hàm/học vị (title) của bác sĩ", example = "2")
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
            description = "Số điện thoại của bác sĩ",
            example = "+84 912 345 678"
    )
    private String phone;

    @Email
    @Schema(description = "Email của bác sĩ", example = "bs.nguyenb@example.com")
    private String email;

    @Schema(description = "Danh sách ID chuyên khoa của bác sĩ", example = "[1, 3, 5]")
    private List<Long> specialtyIds;

    @Schema(description = "Danh sách học vấn")
    private List<EducationDTO> education;

    @Schema(description = "Danh sách kinh nghiệm làm việc")
    private List<ExperienceDTO> workExperience;

    @Schema(description = "Danh sách thành tựu")
    private List<AchievementDTO> achievements;

    @Schema(description = "Danh sách lịch làm việc")
    private List<WorkingHourDTO> workingHours;
}
