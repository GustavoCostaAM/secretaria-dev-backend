package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT s FROM Student s WHERE s.active = true")
    List<Student> findAllStudentsByActiveTrue();

    @Query("SELECT t FROM Teacher t WHERE t.active = true")
    List<Teacher> findAllTeachersByActiveTrue();

    List<User> findAllByActiveTrue();

    List<User> findAllByRoleAndActiveTrue(UserRole role);

    User getUserByEmail(@Email(message = "Email must be valid") @NotBlank(message = "Email must not be null") String email);
}
