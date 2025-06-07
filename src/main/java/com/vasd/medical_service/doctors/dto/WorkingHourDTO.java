package com.vasd.medical_service.doctors.dto;

import com.vasd.medical_service.Enum.DayOfWeekEnum;
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
public class WorkingHourDTO {
    private Integer id;
    @NotNull
    @Schema(description = "Ngày trong tuần")
    private DayOfWeekEnum dayOfWeek;

    @NotBlank
    @Schema(description = "Thời gian bắt đầu, định dạng HH:mm", example = "08:00")
    private String startTime;

    @NotBlank
    @Schema(description = "Thời gian kết thúc, định dạng HH:mm", example = "17:00")
    private String endTime;

    @Schema(description = "Có làm việc hay không", example = "true")
    private Boolean isAvailable;
}
