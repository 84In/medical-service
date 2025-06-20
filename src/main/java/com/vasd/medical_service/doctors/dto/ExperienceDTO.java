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
public class ExperienceDTO {
    private Long id;
    @NotBlank
    @Schema(example = "Bác sĩ điều trị")
    private String position;

    @NotBlank
    @Schema(example = "Bệnh viện Bạch Mai")
    private String organization;

    @NotBlank
    @Schema(example = "2016")
    private String startYear;

    @Schema(example = "2018")
    private String endYear;

    @Schema(example = "Chuyên chữa bệnh mạch vành")
    private String description;
}
