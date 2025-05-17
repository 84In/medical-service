package com.vasd.medical_service.medical_services.service;

import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import com.vasd.medical_service.medical_services.dto.response.ServiceTypeResponseDto;
import com.vasd.medical_service.medical_services.entities.ServiceType;
import com.vasd.medical_service.medical_services.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public List<ServiceTypeResponseDto> getAllServiceTypes() {

        List<ServiceType> serviceTypes = serviceTypeRepository.findAll();

        log.info("getAllServiceTypes");

        return serviceTypes.stream().map(this::mapServiceTypeToDto).collect(Collectors.toList());
    }

    public ServiceTypeResponseDto getServiceTypeById(Long id) {

        ServiceType serviceType = serviceTypeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));

        log.info("getServiceTypeById: {}",serviceType);

        return mapServiceTypeToDto(serviceType);
    }



    private ServiceTypeResponseDto mapServiceTypeToDto(ServiceType serviceType) {

        return ServiceTypeResponseDto.builder()
                .id(serviceType.getId())
                .name(serviceType.getName())
                .description(serviceType.getDescription())
                .status(serviceType.getStatus())
                .build();
    }
}
