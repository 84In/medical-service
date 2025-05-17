package com.vasd.medical_service.medical_services.repository;

import com.vasd.medical_service.medical_services.entities.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {

    Optional<Services> findServiceBySlug(String slug);

    boolean existsServicesBySlug(String slug);
}
