package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDepartmentDto {

  @Schema(
          description = "Tên khoa chuyên môn (null nếu không muốn cập nhật)",
          example = "Khoa Tim mạch"
  )
  private String name;

  @Schema(
          description = "Nội dung chi tiết HTML của khoa (null nếu không muốn cập nhật)",
          example = "<h2>Khoa Tim mạch</h2><p>Đội ngũ bác sĩ...</p>"
  )
  private String contentHtml;

  @Schema(
          description = "Trạng thái (null nếu không muốn cập nhật)",
          allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
          example = "ACTIVE"
  )
  private Status status;
}
