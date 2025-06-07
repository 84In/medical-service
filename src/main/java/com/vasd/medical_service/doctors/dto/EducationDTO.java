package com.vasd.medical_service.doctors.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {
    private Integer id; // null nếu tạo mới
    @NotBlank
    @Schema(example = "Thạc sĩ Y học")
    private String degree;

    @NotBlank
    @Schema(example = "Đại học Y Hà Nội")
    private String institution;

    @NotBlank
    @Schema(example = "2015")
    private String year;

    @Schema(example = "Chuyên ngành tim mạch")
    private String description;
}
