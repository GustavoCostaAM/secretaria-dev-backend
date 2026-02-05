package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.UserRegistrationDTO;
import com.secretaria.secretaria.dto.UserResponseDTO;
import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.service.user.RegisterUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserService registerUserService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRegistrationDTO dto) {
        User user = registerUserService.execute(dto);

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                (user instanceof Student s) ? s.getRegistrationNumber() : null,
                (user instanceof Teacher t && t.getSubject() != null) ? t.getSubject().getName() : null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
