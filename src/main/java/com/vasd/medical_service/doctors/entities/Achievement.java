package com.vasd.medical_service.doctors.entities;
import com.vasd.medical_service.Enum.AchievementType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "achievements")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Achievement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String year;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AchievementType type;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}
