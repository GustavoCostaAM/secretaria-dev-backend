package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject getSubjectsById(Long id);
}
