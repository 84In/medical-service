package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {
    void deleteByDoctorId(Long doctorId);

    List<Education> findByDoctorId(Long doctorId);
}
