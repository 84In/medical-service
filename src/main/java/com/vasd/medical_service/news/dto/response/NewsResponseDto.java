package com.vasd.medical_service.news.dto.response;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@Schema(description = "Thông tin chi tiết của bài viết tin tức")
public class NewsResponseDto {

    @Schema(description = "ID của tin tức", example = "10")
    private Long id;

    @Schema(description = "Slug định danh", example = "gioi-thieu-dich-vu-moi")
    private String slug;

    @Schema(description = "Tên bài viết", example = "Giới thiệu dịch vụ mới")
    private String name;

    @Schema(description = "URL ảnh thumbnail", example = "https://example.com/image.jpg")
    private String thumbnailUrl;

    @Schema(description = "Mô tả ngắn", example = "Dịch vụ khám bệnh chất lượng cao")
    private String descriptionShort;

    @Schema(description = "Nội dung HTML chi tiết", example = "<p>Chi tiết dịch vụ...</p>")
    private String contentHtml;

    @Schema(description = "Trạng thái", example = "ACTIVE")
    private Status status;

    @Schema(description = "Loại tin tức", example = "{01, tin tức y khoa}")
    private NewsTypeResponseDto newsType;

    @Schema(description = "Ngày tạo tin")
    private LocalDateTime createdAt;

    @Schema(description = "Ngày cập nhật tin tức")
    private LocalDateTime updatedAt;
}
