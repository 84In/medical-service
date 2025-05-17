package com.vasd.medical_service.medical_services.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateServiceDto {

    @Schema(
            description = "Slug dùng cho URL, không có dấu và cách nhau bằng dấu gạch ngang",
            example = "kham-noi-tong-quat"
    )
    private String slug;

    @Schema(
            description = "Tên của dịch vụ y tế",
            example = "Khám nội tổng quát"
    )
    private String name;

    @Schema(
            description = "URL ảnh thumbnail của dịch vụ",
            example = "https://cdn.example.com/images/thumbnail.jpg"
    )
    private String thumbnailUrl;

    @Schema(
            description = "Mô tả ngắn gọn của dịch vụ, dùng để hiển thị nhanh",
            example = "Dịch vụ khám tổng quát nội khoa giúp phát hiện sớm bệnh lý tiềm ẩn"
    )
    private String descriptionShort;

    @Schema(
            description = "Nội dung chi tiết (HTML) của dịch vụ",
            example = "<h2>Khám nội tổng quát</h2><p>Chúng tôi cung cấp dịch vụ...</p>"
    )
    private String contentHtml;

    @Schema(
            description = "Trạng thái của dịch vụ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;

    @Schema(
            description = "ID của loại dịch vụ (service type)",
            example = "2"
    )
    private Long serviceTypeId;
}
