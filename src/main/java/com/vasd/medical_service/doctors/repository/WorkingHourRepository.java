package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingHourRepository  extends JpaRepository<WorkingHour, Long> {
    void deleteByDoctorId(Long doctorId);

    List<WorkingHour> findByDoctorId(Long doctorId);
}
