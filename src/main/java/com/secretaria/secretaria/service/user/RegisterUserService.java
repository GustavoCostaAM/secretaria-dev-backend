package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.dto.user.UserRegistrationDTO;
import com.secretaria.secretaria.model.*;
import com.secretaria.secretaria.repository.EnrollmentRepository;
import com.secretaria.secretaria.repository.SubjectRepository;
import com.secretaria.secretaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    public final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User execute(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User newUser;
        if (dto.role() == UserRole.STUDENT) {
            Enrollment enrollment = enrollmentRepository.findByCode(dto.enrollmentCode())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Invalid enrollment code"
                    ));


        if (enrollment.getActiveStudent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Enrollment is already active"
            );
        }

            System.out.println((long) enrollment.getCode());

            newUser = Student.builder()
                    .name(dto.name())
                    .username(dto.username())
                    .email(dto.email())
                    .password(encryptedPassword)
                    .role(UserRole.STUDENT)
                    .registrationNumber(enrollment.getCode().longValue())
                    .active(true)
                    .build();

            enrollment.setActiveStudent(true);
            enrollmentRepository.save(enrollment);
        } else if(dto.role() == UserRole.TEACHER) {
            newUser = Teacher.builder()
                    .name(dto.name())
                    .username(dto.username())
                    .email(dto.email())
                    .password(encryptedPassword)
                    .role(UserRole.TEACHER)
                    .active(true)
                    .build();

            Optional<Subject> subjectOpt = subjectRepository.findById(dto.subjectId());
            System.out.println(dto.subjectId());
            if (subjectOpt.isEmpty()) {
                throw new RuntimeException("Subject not found");
            }

            Subject subject = subjectOpt.get();

            subject.setTeacher((Teacher) newUser);

            subjectRepository.save(subject);

        } else {
            newUser = Admin.builder()
                    .name(dto.name())
                    .username(dto.username())
                    .email(dto.email())
                    .password(encryptedPassword)
                    .role(UserRole.ADM)
                    .active(true)
                    .build();
        }

        return userRepository.save(newUser);
    }
}
