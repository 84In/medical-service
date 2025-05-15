package com.vasd.medical_service.doctors.service;

import com.vasd.medical_service.doctors.dto.request.CreatePositionDto;
import com.vasd.medical_service.doctors.dto.request.UpdatePositionDto;
import com.vasd.medical_service.doctors.dto.response.PositionResponseDto;
import com.vasd.medical_service.doctors.entities.Position;
import com.vasd.medical_service.doctors.repository.PositionRepository;
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
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionResponseDto createPosition(CreatePositionDto request) {

        Position position = new Position();
        position.setName(request.getName());
        position.setDescription(request.getDescription());
        position.setStatus(1);

        log.info("Creating position {}", position);

        return mapPositionToDto(positionRepository.save(position));
    }

    public List<PositionResponseDto> getAllPositions() {
        List<Position> positions = positionRepository.findAll();

        log.info("Getting all positions");
        return positions.stream().map(this::mapPositionToDto).collect(Collectors.toList());

    }

    public PositionResponseDto getPosition(Long id){

        Position position = positionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));

        log.info("Getting position {}", position);
        return mapPositionToDto(position);
    }

    public PositionResponseDto updatePosition(Long id, UpdatePositionDto updatePositionDto) {

        Position position = positionRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POSITION_NOT_FOUND));

        if(updatePositionDto.getName() != null){
            position.setName(updatePositionDto.getName());
        }

        if(updatePositionDto.getDescription() != null){
            position.setDescription(updatePositionDto.getDescription());
        }

        if(updatePositionDto.getStatus() != null){
            position.setStatus(updatePositionDto.getStatus());
        }

        log.info("Updating position {}", position);

        return mapPositionToDto(positionRepository.save(position));
    }

    public void deletePosition(Long id){
        positionRepository.deleteById(id);
        log.info("Deleting position {}", id);
    }

    private PositionResponseDto mapPositionToDto(Position position) {

        return PositionResponseDto.builder()
                .id(position.getId())
                .name(position.getName())
                .description(position.getDescription())
                .status(position.getStatus())
                .build();
    }
}
