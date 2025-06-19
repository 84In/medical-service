package com.vasd.medical_service.upload.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "temporary_images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemporaryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String publicId;
    private String url;

    private boolean used = false;

    private LocalDateTime uploadedAt = LocalDateTime.now();
}
