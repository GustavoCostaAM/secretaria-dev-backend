package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer>{
    Optional<Enrollment> findByCode(int code);
}
