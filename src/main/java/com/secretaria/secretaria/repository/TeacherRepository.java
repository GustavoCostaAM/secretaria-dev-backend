package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher getTeachersById(Long id);
=======
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

>>>>>>> main
}
