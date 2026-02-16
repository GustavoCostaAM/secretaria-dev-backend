package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Subject;
import com.secretaria.secretaria.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByTeacher(Teacher teacher);
    boolean existsByTeacher(Teacher teacher);
    Subject findByIdAndTeacher_Id(Long id, Long teacherId);
}
