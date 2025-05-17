package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDoctorDto {

    @Schema(description = "Tên bác sĩ (null nếu không cập nhật)", example = "Nguyễn Văn A")
    private String name;

    @Schema(description = "URL ảnh đại diện (null nếu không cập nhật)", example = "https://example.com/avatar.jpg")
    private String avatarUrl;

    @Schema(description = "Giới thiệu ngắn về bác sĩ (null nếu không cập nhật)", example = "Bác sĩ chuyên khoa tim mạch, có 10 năm kinh nghiệm.")
    private String introduction;

    @Schema(description = "Số năm kinh nghiệm (null nếu không cập nhật)", example = "10")
    private String experience_years;

    @Schema(description = "ID khoa chuyên môn (null nếu không cập nhật)", example = "3")
    private Long department_id;

    @Schema(description = "ID chức vụ (null nếu không cập nhật)", example = "2")
    private Long position_id;

    @Schema(description = "ID học hàm/học vị (null nếu không cập nhật)", example = "1")
    private Long title_id;

    @Schema(
            description = "Trạng thái bác sĩ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3) (null nếu không cập nhật)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
