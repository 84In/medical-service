package com.vasd.medical_service.doctors.dto.response;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.dto.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DoctorResponseDto {

    private Long id;
    private String name;
    private String avatarUrl;
    private String introduction;
    private String experienceYears;
    private Status status;
    private String phone;
    private String email;

    private DepartmentResponseDto department;
    private PositionResponseDto position;
    private TitleResponseDto title;

    private List<SpecialtyDto> specialties;
    private List<EducationDTO> education;
    private List<ExperienceDTO> workExperience;
    private List<AchievementDTO> achievements;
    private List<WorkingHourDTO> workingHours;
}
