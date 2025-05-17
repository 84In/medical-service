package com.vasd.medical_service.medical_services.repository;

import com.vasd.medical_service.medical_services.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {
}
