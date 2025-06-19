package com.vasd.medical_service.upload.service;

import com.vasd.medical_service.common.utils.CloudinaryUtils;
import com.vasd.medical_service.upload.repository.TemporaryImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageUsageProcessor {
    private final TemporaryImageRepository imageRepository;

    public void processImagesFromHtml(String html) {
        var urls = CloudinaryUtils.extractImageUrlsFromHtml(html);

        urls.forEach(url -> {
            String publicId = CloudinaryUtils.extractPublicIdFromUrl(url);
            imageRepository.findByPublicId(publicId).ifPresent(img -> {
                img.setUsed(true);
                imageRepository.save(img);
            });
        });

        log.info("Marked {} image(s) as used from HTML", urls.size());
    }

    public void processImageUrl(String url) {
        if (url == null || url.contains("/placeholder.svg")) {
            log.info("Marked image as used: {}", url);
            return;
        }
        String publicId = CloudinaryUtils.extractPublicIdFromUrl(url);
        imageRepository.findByPublicId(publicId).ifPresent(img -> {
            img.setUsed(true);
            imageRepository.save(img);
        });
        log.info("Marked image as used: {}", url);
    }
}
