package com.vasd.medical_service.doctors.entities;
import com.vasd.medical_service.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "specialties")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Specialty {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;
}
