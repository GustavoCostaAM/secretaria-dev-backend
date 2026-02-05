package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher getTeachersById(Long id);
}
