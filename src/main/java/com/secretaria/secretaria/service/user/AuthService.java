package com.secretaria.secretaria.service.user;

import com.secretaria.secretaria.dto.user.LoginRequestDTO;
import com.secretaria.secretaria.dto.user.LoginResponseDTO;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                dto.username(),
                dto.password()
        );
        Authentication auth = authenticationManager.authenticate(authToken);
        Object principal = auth.getPrincipal();

        if (!(principal instanceof User user)) {
            throw new RuntimeException("Failed to retrieve authenticated user details.");
        }
        String token = tokenService.generateToken(user);

        return new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                token,
                user.isActive()
        );
    }
}
