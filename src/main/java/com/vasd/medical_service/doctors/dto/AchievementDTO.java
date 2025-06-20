package com.vasd.medical_service.doctors.dto;

import com.vasd.medical_service.Enum.AchievementType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementDTO {
    private Long id;
    @NotBlank
    @Schema(example = "Giải thưởng Bác sĩ xuất sắc")
    private String title;

    @NotBlank
    @Schema(example = "2019")
    private String year;

    @Schema(example = "Được trao bởi Bộ Y tế")
    private String description;

    @NotNull
    @Schema(description = "Loại thành tích", allowableValues = {"AWARD", "CERTIFICATION", "PUBLICATION", "RESEARCH"})
    private AchievementType type;
}
