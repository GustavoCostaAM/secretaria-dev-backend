package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.model.Enrollment;
import com.secretaria.secretaria.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateEnrollmentService {
    public final EnrollmentRepository enrollmentRepository;

    public Enrollment execute(Integer code){
        Enrollment enrollment = enrollmentRepository.findByCode(code).orElseThrow(
                () -> new RuntimeException("Invalid enrollment code")
        );

        if(enrollment.getActiveStudent()){
            throw new RuntimeException("Enrollment is already active.");
        }

        return enrollment;
    }
}
