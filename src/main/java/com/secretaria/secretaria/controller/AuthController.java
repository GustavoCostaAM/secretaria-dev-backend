package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.user.LoginRequestDTO;
import com.secretaria.secretaria.dto.user.LoginResponseDTO;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.service.user.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        User user = authService.authenticate(dto);

        LoginResponseDTO response = new LoginResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole(),
                null
        );

        return ResponseEntity.ok(response);
    }
}
