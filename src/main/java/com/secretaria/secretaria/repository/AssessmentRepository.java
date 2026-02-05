package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Assessment;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Integer> {
    //loads all the assessments by the userId
    List<Assessment> findAllByStudent_Id(Long studentId);

    //loads all the assessments that is after the parameter
    List<Assessment> findAllByDateAfter(@NotNull LocalDate dateAfter);
}
