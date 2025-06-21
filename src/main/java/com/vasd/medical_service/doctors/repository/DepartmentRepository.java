package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.doctors.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("""
    SELECT dp FROM Department dp
    WHERE (
        :keyword IS NULL 
        OR LOWER(dp.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(dp.contentHtml) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR dp.status = :status)
""")
    Page<Department> searchDepartments(
            @Param("keyword") String keyword,
            @Param("status") Status status,
            Pageable pageable
    );



}
