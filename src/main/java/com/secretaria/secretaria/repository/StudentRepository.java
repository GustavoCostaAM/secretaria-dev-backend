package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student getStudentById(Long id);
}
