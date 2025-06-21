package com.vasd.medical_service.doctors.entities;

import com.vasd.medical_service.Enum.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "departments")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "content_html", columnDefinition = "LONGTEXT")
    private String contentHtml;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private Status status;
}
