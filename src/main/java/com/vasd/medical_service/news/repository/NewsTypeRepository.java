package com.vasd.medical_service.news.repository;

import com.vasd.medical_service.news.entities.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsTypeRepository extends JpaRepository<NewsType, Long> {
}
