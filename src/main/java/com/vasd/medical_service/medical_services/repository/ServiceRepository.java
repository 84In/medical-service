package com.vasd.medical_service.medical_services.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.medical_services.entities.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long> {

    Optional<Services> findServiceBySlug(String slug);

    boolean existsServicesBySlug(String slug);

    @Query("""
    SELECT s FROM Services s
    WHERE (
        :keyword IS NULL
        OR LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.descriptionShort) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.contentHtml) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.thumbnailUrl) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.slug) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.serviceType.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(s.serviceType.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR s.status = :status)
    AND (:serviceTypeId IS NULL OR s.serviceType.id = :serviceTypeId)
""")
    Page<Services> searchServices(@Param("keyword") String keyword,
                                         @Param("status") Status status,
                                         @Param("serviceTypeId")Long serviceTypeId,
                                         Pageable pageable);

    Optional<Services> findByIdAndStatusNotIn(Long id, Collection<Status> statuses);
}
