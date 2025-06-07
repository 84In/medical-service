package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    void deleteByDoctorId(Long doctorId);

    List<Experience> findByDoctorId(Long doctorId);
}
