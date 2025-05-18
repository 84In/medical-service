package com.vasd.medical_service.news.entities;

import com.vasd.medical_service.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "news", uniqueConstraints = @UniqueConstraint(columnNames = "slug"))
public class News {

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
    @JoinColumn(name = "news_type_id")
    private NewsType newsType;
}
