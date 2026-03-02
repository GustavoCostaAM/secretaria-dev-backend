package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindUserByIdService {
    private final UserRepository userRepository;

    public Optional<User> findUserById(long id){
        return userRepository.findById(id);
    }
}
