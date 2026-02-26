package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.model.UserRole;
import com.secretaria.secretaria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListUserService {
    private final UserRepository userRepository;

    public List<User> getActiveUsersByRole(UserRole role) {
        return userRepository.findAllByRoleAndActiveTrue(role);
    }

    public List<User> getActiveUsers() {
        return userRepository.findAllByActiveTrue();
    }
}
