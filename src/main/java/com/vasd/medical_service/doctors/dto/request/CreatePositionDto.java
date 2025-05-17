package com.vasd.medical_service.doctors.dto.request;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePositionDto {

    @NotBlank
    @Size(max = 100)
    @Schema(
            description = "Tên chức vụ của bác sĩ",
            example = "Trưởng khoa"
    )
    private String name;

    @Schema(
            description = "Mô tả chi tiết về chức vụ",
            example = "Chịu trách nhiệm quản lý khoa và điều hành công tác chuyên môn"
    )
    private String description;

    @NotNull
    @Schema(
            description = "Trạng thái của chức vụ (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
