package com.vasd.medical_service.cron;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vasd.medical_service.upload.entities.TemporaryImage;
import com.vasd.medical_service.upload.repository.TemporaryImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ImageCleanupTask {

    private final Cloudinary cloudinary;
    private final TemporaryImageRepository imageRepository;

    @Scheduled(fixedRate = 60 * 60 * 1000) // mỗi 1h
    public void cleanupUnusedImages() {
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        List<TemporaryImage> unusedImages = imageRepository.findByUsedFalseAndUploadedAtBefore(oneHourAgo);

        for (TemporaryImage img : unusedImages) {
            try {
                cloudinary.uploader().destroy(img.getPublicId(), ObjectUtils.emptyMap());
                imageRepository.delete(img);
                log.info("Deleted unused image: {}", img.getPublicId());
            } catch (Exception e) {
                log.error("Failed to delete image: {}", img.getPublicId(), e);
            }
        }
    }
}