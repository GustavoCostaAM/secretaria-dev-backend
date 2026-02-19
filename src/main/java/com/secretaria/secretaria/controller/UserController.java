package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.user.UserRegistrationDTO;
import com.secretaria.secretaria.dto.user.UserResponseDTO;
import com.secretaria.secretaria.dto.user.UserUpdateDTO;
import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.service.user.DeactivateUserService;
import com.secretaria.secretaria.service.user.RegisterUserService;
import com.secretaria.secretaria.service.user.UpdateUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserService registerUserService;
    private final DeactivateUserService deleteUserService;
    private final UpdateUserService updateUserService;

    @PostMapping("/registerStudent")
    public ResponseEntity<UserResponseDTO> registerStudent(@RequestBody @Valid UserRegistrationDTO dto) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        deleteUserService.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody UserUpdateDTO dto) {
        User updatedUser = updateUserService.execute(id, dto);
        UserResponseDTO response = new UserResponseDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                updatedUser.getRole(),
                (updatedUser instanceof Student s) ? s.getRegistrationNumber() : null,
                (updatedUser instanceof Teacher t && t.getSubject() != null) ? t.getSubject().getName() : null
        );

        return ResponseEntity.ok().body(response);
    }
}
