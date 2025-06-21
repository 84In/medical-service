package com.vasd.medical_service.doctors.service;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.dto.SpecialtyDto;
import com.vasd.medical_service.doctors.dto.request.CreateTitleDto;
import com.vasd.medical_service.doctors.dto.request.UpdateTitleDto;
import com.vasd.medical_service.doctors.dto.response.TitleResponseDto;
import com.vasd.medical_service.doctors.entities.Specialty;
import com.vasd.medical_service.doctors.entities.Title;
import com.vasd.medical_service.doctors.repository.TitleRepository;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TitleService {

    private final TitleRepository titleRepository;

    public TitleResponseDto creatTitle(CreateTitleDto creatTitleDto) {

        Title title = new Title();
        title.setName(creatTitleDto.getName());
        title.setDescription(creatTitleDto.getDescription());
        title.setStatus(creatTitleDto.getStatus());
        log.info("Creating title {}", title);
        return mapTitleToTitleDto(titleRepository.save(title));
    }

    public List<TitleResponseDto> getAllTitles() {

        List<Title> titles = titleRepository.findAll();
        log.info("Getting all titles");
        return titles.stream().map(this::mapTitleToTitleDto).collect(Collectors.toList());
    }

    public TitleResponseDto getTitleById(Long id) {

        Title title = titleRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.TITLE_NOT_FOUND));
        log.info("Getting title {}", title);
        return mapTitleToTitleDto(title);
    }
    public Page<TitleResponseDto> getAllTitles(Pageable pageable, String keyword, Status status) {
        Page<Title> titles = titleRepository.searchTitle(
                keyword != null && !keyword.trim().isEmpty() ? keyword.trim() : null,
                status,
                pageable
        );
        log.info("Get all specialties!");
        return titles.map(this::mapTitleToTitleDto);
    }

    public TitleResponseDto updateTitle(Long id,UpdateTitleDto updateTitleDto) {

        Title title = titleRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TITLE_NOT_FOUND));
        if (updateTitleDto.getName() != null) {
            title.setName(updateTitleDto.getName());
        }
        if (updateTitleDto.getDescription() != null) {
            title.setDescription(updateTitleDto.getDescription());
        }
        if (updateTitleDto.getStatus() != null) {
            title.setStatus(updateTitleDto.getStatus());
        }
        log.info("Updating title {}", title);
        return mapTitleToTitleDto(titleRepository.save(title));
    }

    public void deleteTitle(Long id) {
        log.info("Removing title {}", id);
        titleRepository.deleteById(id);
    }

    private TitleResponseDto mapTitleToTitleDto(Title title){

        return TitleResponseDto.builder()
                .id(title.getId())
                .name(title.getName())
                .description(title.getDescription())
                .status(title.getStatus())
                .build();
    }
}
