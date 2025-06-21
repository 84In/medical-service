package com.vasd.medical_service.doctors.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "experiences")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Experience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;

    private String organization;

    private String startYear;

    private String endYear;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;
}
