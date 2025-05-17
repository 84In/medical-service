package com.vasd.medical_service.doctors.entities;

import com.vasd.medical_service.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "titles")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;
}
