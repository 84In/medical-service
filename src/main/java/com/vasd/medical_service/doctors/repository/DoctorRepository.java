package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
    SELECT d FROM Doctor d
    LEFT JOIN d.position p
    LEFT JOIN d.title t
    LEFT JOIN d.department dept
    WHERE 
        (:keyword IS NULL OR :keyword = '' OR 
            LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(d.introduction) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(dept.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        AND (:status IS NULL OR d.status = :status)
        AND (:departmentId IS NULL OR dept.id = :departmentId)
""")
    Page<Doctor> searchDoctorsSimple(
            @Param("keyword") String keyword,
            @Param("status") Status status,
            @Param("departmentId") Long departmentId,
            Pageable pageable
    );
    @Query("""
    SELECT DISTINCT d.id FROM Doctor d
    LEFT JOIN d.position p
    LEFT JOIN d.title t
    LEFT JOIN d.department dept
    LEFT JOIN d.specialties s
    WHERE 
        (:keyword IS NULL OR :keyword = '' OR 
            LOWER(d.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(d.introduction) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(dept.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
        AND (:status IS NULL OR d.status = :status)
        AND (:departmentId IS NULL OR dept.id = :departmentId)
""")
    Page<Long> searchDoctorIds(
            @Param("keyword") String keyword,
            @Param("status") Status status,
            @Param("departmentId") Long departmentId,
            Pageable pageable
    );
    @Query("""
    SELECT DISTINCT d FROM Doctor d
    LEFT JOIN FETCH d.specialties
    LEFT JOIN FETCH d.achievements
    LEFT JOIN FETCH d.workExperience
    LEFT JOIN FETCH d.education
    LEFT JOIN FETCH d.workingHours
    WHERE d.id IN :ids
""")
    List<Doctor> findAllByIdsWithDetails(@Param("ids") List<Long> ids);

    @Query("""
    SELECT DISTINCT d FROM Doctor d
    LEFT JOIN FETCH d.specialties
    LEFT JOIN FETCH d.achievements
    LEFT JOIN FETCH d.workExperience
    LEFT JOIN FETCH d.education
    LEFT JOIN FETCH d.workingHours
    WHERE d.id = :id
""")
    Optional<Doctor> findByIdWithDetail(@Param("id") Long id);
}
