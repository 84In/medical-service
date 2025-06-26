package com.vasd.medical_service.news.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.news.entities.News;
import com.vasd.medical_service.news.entities.NewsType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsTypeRepository extends JpaRepository<NewsType, Long> {

    @Query("""
    SELECT nt FROM NewsType nt
    WHERE (
        :keyword IS NULL
        OR LOWER(nt.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR nt.status = :status)
""")
    Page<NewsType> searchNewsTypes(@Param("keyword") String keyword,
                          @Param("status") Status status,
                          Pageable pageable);
}
