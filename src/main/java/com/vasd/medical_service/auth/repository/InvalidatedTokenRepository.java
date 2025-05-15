package com.vasd.medical_service.auth.repository;

import com.vasd.medical_service.auth.entities.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
