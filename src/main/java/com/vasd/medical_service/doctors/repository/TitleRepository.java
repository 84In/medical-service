package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, Long> {
}
