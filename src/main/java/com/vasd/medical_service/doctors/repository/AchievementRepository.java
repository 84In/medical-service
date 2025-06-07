package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    void deleteByDoctorId(Long id);

    List<Achievement> findByDoctorId(Long doctorId);
}
