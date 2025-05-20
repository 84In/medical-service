package com.vasd.medical_service.news.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateNewsDto {

    @Schema(description = "Slug định danh duy nhất cho tin tức", example = "gioi-thieu-dich-vu-moi")
    @NotBlank(message = "Slug không được để trống")
    private String slug;

    @Schema(description = "Tên bài viết", example = "Giới thiệu dịch vụ mới")
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @Schema(description = "URL ảnh thumbnail", example = "https://example.com/image.jpg")
    @NotBlank(message = "Thumbnail URL không được để trống")
    private String thumbnailUrl;

    @Schema(description = "Mô tả ngắn gọn", example = "Chúng tôi vừa triển khai một dịch vụ mới giúp chăm sóc sức khỏe tốt hơn")
    @NotBlank(message = "Mô tả ngắn không được để trống")
    private String descriptionShort;

    @Schema(description = "Nội dung HTML", example = "<p>Chi tiết dịch vụ...</p>")
    @NotBlank(message = "Nội dung không được để trống")
    private String contentHtml;

    @Schema(description = "Trạng thái tin tức", defaultValue = "ACTIVE")
    private Status status = Status.ACTIVE;

    @Schema(description = "ID của loại tin tức", example = "1")
    @NotNull(message = "NewsTypeId không được để trống")
    private Long newsTypeId;
}
