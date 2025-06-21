package com.vasd.medical_service.upload.repository;

import com.vasd.medical_service.upload.entities.TemporaryImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TemporaryImageRepository extends JpaRepository<TemporaryImage, Long> {

    List<TemporaryImage> findByUsedFalseAndUploadedAtBefore(LocalDateTime time);
    Optional<TemporaryImage> findByPublicId(String publicId);
}
