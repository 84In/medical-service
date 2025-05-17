package com.vasd.medical_service.medical_services.service;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import com.vasd.medical_service.medical_services.dto.request.CreateServiceDto;
import com.vasd.medical_service.medical_services.dto.request.UpdateServiceDto;
import com.vasd.medical_service.medical_services.dto.response.ServiceResponseDto;
import com.vasd.medical_service.medical_services.dto.response.ServiceTypeResponseDto;
import com.vasd.medical_service.medical_services.entities.ServiceType;
import com.vasd.medical_service.medical_services.entities.Services;
import com.vasd.medical_service.medical_services.repository.ServiceRepository;
import com.vasd.medical_service.medical_services.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public List<ServiceResponseDto> findAllServices() {

        List<Services> services = serviceRepository.findAll();

        return services.stream().map(this::mapServiceToDto).collect(Collectors.toList());
    }

    public ServiceResponseDto getServiceById(Long id) {

        Services service = serviceRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        return mapServiceToDto(service);
    }

    public ServiceResponseDto getServiceBySlug(String slug) {

        Services service = serviceRepository.findServiceBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.SERVICE_NOT_FOUND));

        return mapServiceToDto(service);
    }

    public ServiceResponseDto createService(CreateServiceDto createServiceDto) {

        ServiceType serviceType = serviceTypeRepository.findById(createServiceDto.getServiceTypeId()).orElseThrow(()-> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));


        Services service = new Services();
        service.setSlug(createServiceDto.getSlug());
        service.setName(createServiceDto.getName());
        service.setStatus(Status.ACTIVE);
        service.setContentHtml(createServiceDto.getContentHtml());
        service.setDescriptionShort(createServiceDto.getDescriptionShort());
        service.setThumbnailUrl(createServiceDto.getThumbnailUrl());
        service.setServiceType(serviceType);
        log.info("Create service: {}", service);
        return mapServiceToDto( serviceRepository.save(service));
    }

    public ServiceResponseDto updateService(Long id,UpdateServiceDto updateServiceDto) {
        Services service = serviceRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.SERVICE_NOT_FOUND));
        if (updateServiceDto.getName() != null){
            service.setName(updateServiceDto.getName());
        }
        if (updateServiceDto.getSlug() != null){
            if(serviceRepository.existsServicesBySlug(updateServiceDto.getSlug())){
                throw new AppException(ErrorCode.SLUG_EXISTS);
            }
            service.setSlug(updateServiceDto.getSlug());
        }
        if (updateServiceDto.getContentHtml() != null){
            service.setContentHtml(updateServiceDto.getContentHtml());
        }
        if (updateServiceDto.getDescriptionShort() != null){
            service.setDescriptionShort(updateServiceDto.getDescriptionShort());
        }
        if (updateServiceDto.getThumbnailUrl() != null){
            service.setThumbnailUrl(updateServiceDto.getThumbnailUrl());
        }
        if (updateServiceDto.getServiceTypeId()!= null){
            ServiceType serviceType = serviceTypeRepository.findById(updateServiceDto.getServiceTypeId()).orElseThrow(()-> new AppException(ErrorCode.SERVICE_TYPE_NOT_FOUND));
            service.setServiceType(serviceType);
        }
        if (updateServiceDto.getStatus() != null) {
            service.setStatus(updateServiceDto.getStatus());
        }

        log.info("Update service: {}", service);
        return mapServiceToDto( serviceRepository.save(service));
    }

    public void deleteServiceById(Long id) {
        log.info("Delete service: {}", id);
        serviceRepository.deleteById(id);
    }

    private ServiceResponseDto mapServiceToDto(Services service) {
        return ServiceResponseDto.builder()
                .id(service.getId())
                .name(service.getName())
                .thumbnailUrl(service.getThumbnailUrl())
                .serviceType(ServiceTypeResponseDto.builder()
                        .id(service.getServiceType().getId())
                        .name(service.getServiceType().getName())
                        .description(service.getServiceType().getDescription())
                        .status(service.getServiceType().getStatus())
                        .build())
                .contentHtml(service.getContentHtml())
                .descriptionShort(service.getDescriptionShort())
                .slug(service.getSlug())
                .status(service.getStatus())
                .createdAt(service.getCreatedAt())
                .updatedAt(service.getUpdatedAt())
                .build();
    }
}
