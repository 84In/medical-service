package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.entities.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    List<Specialty> findAllByNameContainingIgnoreCase(String name);

    @Query("""
    SELECT sp FROM Specialty sp
    WHERE (
        :keyword IS NULL 
        OR LOWER(sp.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(sp.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR sp.status = :status)
""")
    Page<Specialty> searchSpecialty(@Param("keyword") String keyword,
                                    @Param("status") Status status,
                                    Pageable pageable);
}
