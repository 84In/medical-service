package com.vasd.medical_service.medical_services.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "service_types")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer status;

    @OneToMany(mappedBy = "serviceType")
    private List<Service> services;

}
