package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.entities.Title;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {

    @Query("""
    SELECT t FROM Title t
    WHERE (
        :keyword IS NULL 
        OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR t.status = :status)
""")
    Page<Title> searchTitle(@Param("keyword") String keyword,
                                    @Param("status") Status status,
                                    Pageable pageable);
}
