package com.secretaria.secretaria.service.enrollment;

import com.secretaria.secretaria.model.Enrollment;
import com.secretaria.secretaria.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenerateEnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public Enrollment execute(){
        Enrollment newestEnrollment = enrollmentRepository.findTopByOrderByCodeDesc();
        Enrollment newEnrollment = Enrollment.builder().code(newestEnrollment.getCode() + 1).activeStudent(false).build();

        return enrollmentRepository.save(newEnrollment);
    }
}
