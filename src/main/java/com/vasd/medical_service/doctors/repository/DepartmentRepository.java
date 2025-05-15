package com.vasd.medical_service.doctors.repository;

import com.vasd.medical_service.doctors.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
