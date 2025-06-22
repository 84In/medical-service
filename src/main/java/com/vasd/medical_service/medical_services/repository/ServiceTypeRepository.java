package com.vasd.medical_service.medical_services.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.medical_services.entities.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

    @Query("""
    SELECT st FROM ServiceType st
    WHERE (
        :keyword IS NULL
        OR LOWER(st.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(st.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR st.status = :status)
""")
    Page<ServiceType> searchServiceTypes(@Param("keyword") String keyword,
                                         @Param("status") Status status,
                                         Pageable pageable);

}
