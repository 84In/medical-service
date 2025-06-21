package com.vasd.medical_service.doctors.service;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.dto.SpecialtyDto;
import com.vasd.medical_service.doctors.entities.Doctor;
import com.vasd.medical_service.doctors.entities.Specialty;
import com.vasd.medical_service.doctors.repository.SpecialtyRepository;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpecialtyService {

    private final SpecialtyRepository specialtyRepository;

    public SpecialtyDto createSpecialty(SpecialtyDto specialtyDto) {
        Specialty specialty = new Specialty();
        specialty.setName(specialtyDto.getName());
        specialty.setDescription(specialtyDto.getDescription());
        specialty.setStatus(specialtyDto.getStatus());
        log.info("Create specialty: {}", specialty);
        return mapToDto(specialtyRepository.save(specialty));
    }

    public List<SpecialtyDto> getAllSpecialties() {
        List<Specialty> specialties = specialtyRepository.findAll();
        log.info("Get all specialties!");
        return specialties.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public SpecialtyDto getSpecialty(Long id) {
        Specialty specialty = specialtyRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SPECIALTY_NOT_FOUND));
        log.info("Get specialty: {}", specialty);
        return mapToDto(specialty);
    }

    public List<SpecialtyDto> getAllSpecialtyByName(String name) {
        List<Specialty> specialty = specialtyRepository.findAllByNameContainingIgnoreCase(name);
        log.info("Get all specialties by name: {}!", name);
        return specialty.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Page<SpecialtyDto> getAllSpecialty(Pageable pageable, String keyword, Status status) {
        Page<Specialty> specialties = specialtyRepository.searchSpecialty(
                keyword != null && !keyword.trim().isEmpty() ? keyword.trim() : null,
                status,
                pageable
        );
        log.info("Get all specialties!");
        return specialties.map(this::mapToDto);
    }

    public SpecialtyDto updateSpecialty(Long id, SpecialtyDto specialtyDto) {
        Specialty specialty = specialtyRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SPECIALTY_NOT_FOUND));
        BeanUtils.copyProperties(specialtyDto, specialty);
        log.info("Update specialty: {}", specialty);
        return mapToDto(specialtyRepository.save(specialty));
    }

    public void deleteSpecialty(Long id) {
        log.info("Delete specialty: {}", id);
        specialtyRepository.deleteById(id);
    }

    private SpecialtyDto mapToDto(Specialty specialty) {
        SpecialtyDto dto = new SpecialtyDto();
        BeanUtils.copyProperties(specialty, dto);
        return dto;
    }
}
