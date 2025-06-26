package com.vasd.medical_service.news.repository;

import com.vasd.medical_service.Enum.Status;
import com.vasd.medical_service.news.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @Query("""
    SELECT n FROM News n
    WHERE (
        :keyword IS NULL
        OR LOWER(n.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.descriptionShort) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.contentHtml) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.thumbnailUrl) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.slug) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(n.newsType.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    AND (:status IS NULL OR n.status = :status)
    AND (:newsTypeId IS NULL OR n.newsType.id = :newsTypeId)
""")
    Page<News> searchNews(@Param("keyword") String keyword,
                                  @Param("status") Status status,
                                  @Param("newsTypeId")Long newsTypeId,
                                  Pageable pageable);

    Optional<News> findBySlugAndStatusNotIn(String slug, Collection<Status> statuses);
}
