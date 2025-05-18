package com.vasd.medical_service.news.service;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import com.vasd.medical_service.news.dto.request.CreateNewsTypeDto;
import com.vasd.medical_service.news.dto.request.UpdateNewsTypeDto;
import com.vasd.medical_service.news.dto.response.NewsTypeResponseDto;
import com.vasd.medical_service.news.entities.NewsType;
import com.vasd.medical_service.news.repository.NewsTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsTypeService {

    private final NewsTypeRepository newsTypeRepository;

    public List<NewsTypeResponseDto> getAllNewsTypes() {

        List<NewsType> newsTypes = newsTypeRepository.findAll();
        log.info("findAllNewsTypes");
        return newsTypes.stream().map(this::mapNewsTypeToDto).collect(Collectors.toList());
    }

    public NewsTypeResponseDto getNewsTypeById(Long id) {

        NewsType newsType = newsTypeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.NEWS_TYPE_NOT_FOUND));
        log.info("findNewsTypeById: {}", newsType);
        return mapNewsTypeToDto(newsType);
    }

    public NewsTypeResponseDto createNewsType(CreateNewsTypeDto createNewsTypeDto) {

        NewsType newsType = new NewsType();
        newsType.setName(createNewsTypeDto.getName());
        newsType.setStatus(Status.ACTIVE);
        log.info("createNewsType: {}", newsType);
        return mapNewsTypeToDto(newsTypeRepository.save(newsType));
    }

    public NewsTypeResponseDto updateNewsType(Long id, UpdateNewsTypeDto updateNewsTypeDto) {

        NewsType newsType = newsTypeRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.NEWS_TYPE_NOT_FOUND));

        if(updateNewsTypeDto.getName() != null) {
            newsType.setName(updateNewsTypeDto.getName());
        }
        if(updateNewsTypeDto.getStatus() != null) {
            newsType.setStatus(updateNewsTypeDto.getStatus());
        }
        log.info("updateNewsType: {}", newsType);
        return mapNewsTypeToDto(newsTypeRepository.save(newsType));
    }

    public void deleteNewsType(Long id) {
        log.info("deleteNewsType: {}", id);
        newsTypeRepository.deleteById(id);
    }

    private NewsTypeResponseDto mapNewsTypeToDto(NewsType newsType) {
        return NewsTypeResponseDto.builder()
                .id(newsType.getId())
                .name(newsType.getName())
                .status(newsType.getStatus())
                .build();
    }
}
