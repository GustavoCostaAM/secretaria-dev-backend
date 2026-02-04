package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.dto.UserRegistrationDTO;
import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.model.UserRole;
import com.secretaria.secretaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User execute(UserRegistrationDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email already in use");
        }

        String encryptedPassword = passwordEncoder.encode(dto.password());

        User newUser;
        if (dto.role() == UserRole.STUDENT) {
            newUser = Student.builder()
                    .name(dto.name())
                    .username(dto.username())
                    .email(dto.email())
                    .password(encryptedPassword)
                    .registrationNumber(dto.registrationNumber())
                    .role(UserRole.STUDENT)
                    .build();
        } else {
            newUser = Teacher.builder()
                    .name(dto.name())
                    .username(dto.username())
                    .email(dto.email())
                    .password(encryptedPassword)
                    .role(UserRole.TEACHER)
                    .build();
        }

        return userRepository.save(newUser);
    }
}
