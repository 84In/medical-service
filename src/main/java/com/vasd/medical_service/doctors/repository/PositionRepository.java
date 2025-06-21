package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.entities.Position;
import com.vasd.medical_service.doctors.entities.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    @Query("""
    SELECT p FROM Position p
    WHERE (
        :keyword IS NULL 
        OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR p.status = :status)
""")
    Page<Position> searchPositions(
            @Param("keyword") String keyword,
            @Param("status") Status status,
            Pageable pageable
    );
}
