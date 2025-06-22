package com.vasd.medical_service.medical_services.service;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import com.vasd.medical_service.medical_services.dto.request.CreateServiceTypeDto;
import com.vasd.medical_service.medical_services.dto.request.UpdateServiceTypeDto;
import com.vasd.medical_service.medical_services.dto.response.ServiceTypeResponseDto;
import com.vasd.medical_service.medical_services.entities.ServiceType;
import com.vasd.medical_service.medical_services.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ServiceTypeResponseDto> getAllServiceTypes(String keyword, Status status, Pageable pageable) {
        Page<ServiceType> serviceTypes = serviceTypeRepository.searchServiceTypes(
                keyword != null && !keyword.trim().isEmpty() ? keyword.trim() : null,
                status,
                pageable
        );
        log.info("getAllServiceTypes keyword: {}, status: {}", keyword, status);
        return serviceTypes.map(this::mapServiceTypeToDto);
    }

    public ServiceTypeResponseDto getServiceTypeById(Long id) {

        ServiceType serviceType = serviceTypeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));

        log.info("getServiceTypeById: {}",serviceType);

        return mapServiceTypeToDto(serviceType);
    }
    public ServiceTypeResponseDto createServiceType(CreateServiceTypeDto createServiceTypeDto) {

        ServiceType serviceType = new ServiceType();
        serviceType.setName(createServiceTypeDto.getName());
        serviceType.setDescription(createServiceTypeDto.getDescription());
        serviceType.setStatus(createServiceTypeDto.getStatus()  );
        serviceTypeRepository.save(serviceType);

        log.info("createServiceType: {}",serviceType);

        return mapServiceTypeToDto(serviceType);
    }

    public ServiceTypeResponseDto updateServiceType(Long id,UpdateServiceTypeDto updateServiceTypeDto) {

        ServiceType serviceType = serviceTypeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));

        if(updateServiceTypeDto.getName() != null){
            serviceType.setName(updateServiceTypeDto.getName());
        }
        if(updateServiceTypeDto.getDescription() != null){
            serviceType.setDescription(updateServiceTypeDto.getDescription());
        }
        if(updateServiceTypeDto.getStatus() != null){
            serviceType.setStatus(updateServiceTypeDto.getStatus());
        }

        log.info("updateServiceType: {}",serviceType);

        return mapServiceTypeToDto(serviceTypeRepository.save(serviceType));
    }

    public void deleteServiceType(Long id) {

        log.info("deleteServiceType: {}",id);
        serviceTypeRepository.deleteById(id);
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
