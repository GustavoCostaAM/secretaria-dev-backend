package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.dto.user.UserUpdateDTO;
import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;

    @Transactional
    public User execute(Long id, UserUpdateDTO dto){
        User user = userRepository.findById(id).filter(User::isActive).orElseThrow(
                () -> new RuntimeException("User not found or not active")
        );

        if (dto.name() != null) user.setName(dto.name());
        if (dto.email() != null) user.setEmail(dto.email());

        if (user instanceof Student student && dto.registrationNumber() != null) {
            student.setRegistrationNumber(dto.registrationNumber());
        }

        return userRepository.save(user);
    }
}
