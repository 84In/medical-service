package com.vasd.medical_service.doctors.service;

import com.vasd.medical_service.doctors.dto.request.CreateDoctorDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDoctorDto;
import com.vasd.medical_service.doctors.dto.response.DepartmentResponseDto;
import com.vasd.medical_service.doctors.dto.response.DoctorResponseDto;
import com.vasd.medical_service.doctors.dto.response.PositionResponseDto;
import com.vasd.medical_service.doctors.dto.response.TitleResponseDto;
import com.vasd.medical_service.doctors.entities.Department;
import com.vasd.medical_service.doctors.entities.Doctor;
import com.vasd.medical_service.doctors.entities.Position;
import com.vasd.medical_service.doctors.entities.Title;
import com.vasd.medical_service.doctors.repository.DepartmentRepository;
import com.vasd.medical_service.doctors.repository.DoctorRepository;
import com.vasd.medical_service.doctors.repository.PositionRepository;
import com.vasd.medical_service.doctors.repository.TitleRepository;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final TitleRepository titleRepository;
    private final PositionRepository positionRepository;

    public DoctorResponseDto createDoctor(CreateDoctorDto createDoctorDto) {
        Doctor doctor = new Doctor();
        doctor.setName(createDoctorDto.getName());
        doctor.setAvatarUrl(createDoctorDto.getAvatarUrl());
        doctor.setIntroduction(createDoctorDto.getIntroduction());
        doctor.setExperience_years(createDoctorDto.getExperience_years());
        doctor.setStatus(1);

        Department department = departmentRepository.findById(createDoctorDto.getDepartment_id())
                .orElseThrow(()-> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        doctor.setDepartment(department);

        Position position = positionRepository.findById(createDoctorDto.getPosition_id())
                .orElseThrow(()-> new AppException(ErrorCode.POSITION_NOT_FOUND));
        doctor.setPosition(position);

        Title title = titleRepository.findById(createDoctorDto.getTitle_id())
                .orElseThrow(()-> new AppException(ErrorCode.TITLE_NOT_FOUND));
        doctor.setTitle(title);

        log.info("Create doctor: {}", doctor);
        return mapDoctorToDto(doctorRepository.save(doctor));
    }

    public List<DoctorResponseDto> getAllDoctors() {

        List<Doctor> doctors = doctorRepository.findAll();

        log.info("Get all doctors");
        return doctors.stream().map(this::mapDoctorToDto).collect(Collectors.toList());

    }

    public DoctorResponseDto getDoctor(Long id) {
        Doctor doctor =  doctorRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.DOCTOR_NOT_FOUND));
        log.info("Get doctor: {}", doctor);
        return mapDoctorToDto(doctor);
    }

    public DoctorResponseDto updateDoctor(Long id,UpdateDoctorDto updateDoctorDto) {

        Doctor doctor = doctorRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.DOCTOR_NOT_FOUND));

        if(updateDoctorDto.getName() != null) {
            doctor.setName(updateDoctorDto.getName());
        }
        if(updateDoctorDto.getAvatarUrl() != null) {
            doctor.setAvatarUrl(updateDoctorDto.getAvatarUrl());
        }
        if(updateDoctorDto.getIntroduction() != null) {
            doctor.setIntroduction(updateDoctorDto.getIntroduction());
        }
        if(updateDoctorDto.getExperience_years() != null) {
            doctor.setExperience_years(updateDoctorDto.getExperience_years());
        }
        if(updateDoctorDto.getStatus() != null){
            doctor.setStatus(updateDoctorDto.getStatus());
        }

        if (updateDoctorDto.getDepartment_id() != null) {
            Department department = departmentRepository.findById(updateDoctorDto.getDepartment_id()).orElseThrow(()-> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
            log.info("Update doctor department: {}", department);
            doctor.setDepartment(department);
        }

        if (updateDoctorDto.getPosition_id() != null) {
            Position position = positionRepository.findById(updateDoctorDto.getPosition_id()).orElseThrow(()-> new AppException(ErrorCode.POSITION_NOT_FOUND));
            log.info("Update doctor position: {}", position);
            doctor.setPosition(position);
        }

        if (updateDoctorDto.getTitle_id() != null) {
            Title title = titleRepository.findById(updateDoctorDto.getTitle_id()).orElseThrow(()-> new AppException(ErrorCode.TITLE_NOT_FOUND));
            log.info("Update doctor title: {}", title);
            doctor.setTitle(title);
        }

        log.info("Update doctor: {}", doctor);

        return mapDoctorToDto(doctorRepository.save(doctor));

    }

    public void deleteDoctor(Long id) {
        log.info("Delete doctor: {}", id);
        doctorRepository.deleteById(id);
    }

    private DoctorResponseDto mapDoctorToDto(Doctor doctor) {

        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .avatarUrl(doctor.getAvatarUrl())
                .introduction(doctor.getIntroduction())
                .experience_years(doctor.getExperience_years())
                .status(doctor.getStatus())
                .department(DepartmentResponseDto.builder()
                        .id(doctor.getDepartment().getId())
                        .name(doctor.getDepartment().getName())
                        .contentHtml(doctor.getDepartment().getContentHtml())
                        .status(doctor.getDepartment().getStatus())
                        .build())
                .position(PositionResponseDto.builder()
                        .id(doctor.getPosition().getId())
                        .name(doctor.getPosition().getName())
                        .status(doctor.getPosition().getStatus())
                        .description(doctor.getPosition().getDescription())
                        .build())
                .title(TitleResponseDto.builder()
                        .id(doctor.getTitle().getId())
                        .name(doctor.getTitle().getName())
                        .status(doctor.getTitle().getStatus())
                        .description(doctor.getTitle().getDescription())
                        .build())
                .build();

    }
}
