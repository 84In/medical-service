package com.vasd.medical_service.doctors.dto;

import com.vasd.medical_service.Enum.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyDto {
    @Schema(description = "ID chuyên môn", example = "1")
    private Long id;

    @Schema(description = "Tên chuyên môn", example = "Tim mạch")
    private String name;

    @Schema(description = "Mô tả chuyên môn", example = "Chẩn đoán và điều trị các bệnh lý tim mạch")
    private String description;

    @NotNull
    @Schema(
            description = "Trạng thái của chuyên môn (INACTIVE=0, ACTIVE=1, DELETED=2, HIDDEN=3)",
            allowableValues = {"INACTIVE", "ACTIVE", "DELETED", "HIDDEN"},
            example = "ACTIVE"
    )
    private Status status;
}
