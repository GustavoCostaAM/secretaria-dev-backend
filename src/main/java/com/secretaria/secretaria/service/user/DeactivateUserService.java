package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeactivateUserService {
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setActive(false);

        userRepository.save(user);
    }
}
