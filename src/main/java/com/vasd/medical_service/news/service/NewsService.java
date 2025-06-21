package com.vasd.medical_service.news.service;

import com.vasd.medical_service.exception.AppException;
import com.vasd.medical_service.exception.ErrorCode;
import com.vasd.medical_service.news.dto.request.CreateNewsDto;
import com.vasd.medical_service.news.dto.request.UpdateNewsDto;
import com.vasd.medical_service.news.dto.response.NewsResponseDto;
import com.vasd.medical_service.news.dto.response.NewsTypeResponseDto;
import com.vasd.medical_service.news.entities.News;
import com.vasd.medical_service.news.entities.NewsType;
import com.vasd.medical_service.news.repository.NewsRepository;
import com.vasd.medical_service.news.repository.NewsTypeRepository;
import com.vasd.medical_service.upload.service.ImageUsageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsTypeRepository newsTypeRepository;
    private final ImageUsageProcessor imageUsageProcessor;

    public List<NewsResponseDto> getAllNews() {

        List<News> news = newsRepository.findAll();
        log.info("Get all list of news");
        return news.stream().map(this::newsToDto).collect(Collectors.toList());
    }

    public NewsResponseDto getNewsById(Long id) {

        News news = newsRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));
        log.info("News found: {}", news);
        return newsToDto(news);
    }

    public NewsResponseDto createNews(CreateNewsDto createNewsDto) {

        NewsType newsType = newsTypeRepository.findById(createNewsDto.getNewsTypeId()).orElseThrow(() -> new AppException(ErrorCode.NEWS_TYPE_NOT_FOUND));

        News news = new News();
        news.setId(news.getId());
        news.setName(news.getName());
        news.setContentHtml(news.getContentHtml());
        news.setSlug(news.getSlug());
        news.setDescriptionShort(news.getDescriptionShort());
        news.setNewsType(newsType);
        news.setThumbnailUrl(news.getThumbnailUrl());
        news.setStatus(news.getNewsType().getStatus());
        log.info("News created: {}", news);
        imageUsageProcessor.processImageUrl(news.getThumbnailUrl());
        imageUsageProcessor.processImagesFromHtml(news.getContentHtml());
        return newsToDto(news);
    }

    public NewsResponseDto updateNews(Long id, UpdateNewsDto updateNewsDto) {

        News news = newsRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.NEWS_NOT_FOUND));

        if(updateNewsDto.getName()!= null){
            news.setName(updateNewsDto.getName());
        }
        if(updateNewsDto.getContentHtml()!= null){
            news.setContentHtml(updateNewsDto.getContentHtml());
            imageUsageProcessor.processImagesFromHtml(updateNewsDto.getContentHtml());
        }
        if(updateNewsDto.getSlug()!= null){
            news.setSlug(updateNewsDto.getSlug());
        }
        if(updateNewsDto.getDescriptionShort()!= null){
            news.setDescriptionShort(updateNewsDto.getDescriptionShort());
        }
        if(updateNewsDto.getNewsTypeId() != null){
            NewsType newsType = newsTypeRepository.findById(updateNewsDto.getNewsTypeId()).orElseThrow(() -> new AppException(ErrorCode.NEWS_TYPE_NOT_FOUND));
            news.setNewsType(newsType);
        }
        if(updateNewsDto.getThumbnailUrl() != null){
            news.setThumbnailUrl(updateNewsDto.getThumbnailUrl());
            imageUsageProcessor.processImageUrl(news.getThumbnailUrl());
        }
        if(updateNewsDto.getStatus() != null){
            news.setStatus(updateNewsDto.getStatus());
        }
        log.info("News updated: {}", news);
        return newsToDto(news);
    }

    public void deleteNews(Long id) {
        log.info("Deleting news with id: {}", id);
        newsRepository.deleteById(id);
    }

    private NewsResponseDto newsToDto(News news) {
        return NewsResponseDto.builder()
                .id(news.getId())
                .name(news.getName())
                .contentHtml(news.getContentHtml())
                .slug(news.getSlug())
                .descriptionShort(news.getDescriptionShort())
                .newsType(NewsTypeResponseDto.builder()
                        .id(news.getNewsType().getId())
                        .name(news.getNewsType().getName())
                        .status(news.getNewsType().getStatus())
                        .build())
                .thumbnailUrl(news.getThumbnailUrl())
                .status(news.getNewsType().getStatus())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }
}
