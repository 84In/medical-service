package com.vasd.medical_service.news.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateNewsDto {

    @Schema(description = "Slug định danh duy nhất cho tin tức", example = "gioi-thieu-dich-vu-moi", nullable = true)
    private String slug;

    @Schema(description = "Tên bài viết", example = "Giới thiệu dịch vụ mới", nullable = true)
    private String name;

    @Schema(description = "URL ảnh thumbnail", example = "https://example.com/image.jpg", nullable = true)
    private String thumbnailUrl;

    @Schema(description = "Mô tả ngắn gọn", example = "Chúng tôi vừa triển khai một dịch vụ mới", nullable = true)
    private String descriptionShort;

    @Schema(description = "Nội dung HTML", example = "<p>Chi tiết dịch vụ...</p>", nullable = true)
    private String contentHtml;

    @Schema(description = "Trạng thái tin tức", example = "INACTIVE", nullable = true)
    private Status status;

    @Schema(description = "ID của loại tin tức", example = "1", nullable = true)
    private Long newsTypeId;
}
