package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
    SELECT d FROM Doctor d
    WHERE 
        LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(d.introduction) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(d.title.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(d.department.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Doctor> searchDoctors(@Param("keyword") String keyword, Pageable pageable);
}
