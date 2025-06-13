package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    List<Specialty> findAllByNameContainingIgnoreCase(String name);
}
