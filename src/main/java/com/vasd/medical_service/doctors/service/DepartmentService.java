package com.vasd.medical_service.doctors.service;

import com.vasd.medical_service.doctors.dto.request.CreateDepartmentDto;
import com.vasd.medical_service.doctors.dto.request.UpdateDepartmentDto;
import com.vasd.medical_service.doctors.dto.response.DepartmentResponseDto;
import com.vasd.medical_service.doctors.entities.Department;
import com.vasd.medical_service.doctors.repository.DepartmentRepository;
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
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponseDto createDepartment(CreateDepartmentDto createDepartmentDto) {
        Department department = new Department();
        department.setName(createDepartmentDto.getName());
        department.setContentHtml(createDepartmentDto.getContentHtml());
        department.setStatus(1);
        log.info("Create department success {}", department);
        return mapDepartmentToResponseDto(departmentRepository.save(department));
    }

    public List<DepartmentResponseDto> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        log.info("Get all department success");
        return departments.stream().map(this::mapDepartmentToResponseDto).collect(Collectors.toList());
    }

    public DepartmentResponseDto getDepartment(Long id) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        log.info("Get department success {}", department);
        return mapDepartmentToResponseDto(department);
    }

    public DepartmentResponseDto updateDepartment(Long id,UpdateDepartmentDto updateDepartmentDto) {

        Department department = departmentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_FOUND));
        if (updateDepartmentDto.getName() != null) {
            department.setName(updateDepartmentDto.getName());
        }
        if (updateDepartmentDto.getContentHtml() != null) {
            department.setContentHtml(updateDepartmentDto.getContentHtml());
        }
        if (updateDepartmentDto.getStatus() != null) {
            department.setStatus(updateDepartmentDto.getStatus());
        }
        log.info("Update department success {}", department);
        return mapDepartmentToResponseDto(departmentRepository.save(department));

    }

    public void deleteDepartment(Long id) {
        log.info("Delete department success {}", id);
        departmentRepository.deleteById(id);
    }

    private DepartmentResponseDto mapDepartmentToResponseDto(Department department) {
        return DepartmentResponseDto.builder()
                .id(department.getId())
                .name(department.getName())
                .contentHtml(department.getContentHtml())
                .status(department.getStatus())
                .build();
    }
}
