package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.dto.user.LoginRequestDTO;
import com.secretaria.secretaria.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;

    public User authenticate(LoginRequestDTO dto) {
        var authToken = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());

        Authentication auth = authenticationManager.authenticate(authToken);

        return (User) auth.getPrincipal();
    }
}
