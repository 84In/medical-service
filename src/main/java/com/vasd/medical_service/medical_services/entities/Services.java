package com.vasd.medical_service.medical_services.entities;

import com.vasd.medical_service.Enum.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services",
        uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String slug;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 255)
    private String thumbnailUrl;

    @Lob
    private String descriptionShort;

    @Lob
    private String contentHtml;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;
}
