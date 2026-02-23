package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.user.UserRegistrationDTO;
import com.secretaria.secretaria.dto.user.UserResponseDTO;
import com.secretaria.secretaria.dto.user.UserUpdateDTO;
import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.model.UserRole;
import com.secretaria.secretaria.service.user.DeactivateUserService;
import com.secretaria.secretaria.service.user.ListUserService;
import com.secretaria.secretaria.service.user.RegisterUserService;
import com.secretaria.secretaria.service.user.UpdateUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final RegisterUserService registerUserService;
    private final DeactivateUserService deleteUserService;
    private final UpdateUserService updateUserService;
    private final ListUserService listUserService;

    @PostMapping("/registerStudent")
    public ResponseEntity<UserResponseDTO> registerStudent(@RequestBody @Valid UserRegistrationDTO dto) {
        if(dto.role() != UserRole.STUDENT){
            throw new RuntimeException("This endpoint is only for student registration.");
        }
        User user = registerUserService.execute(dto);

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                ((Student) user).getRegistrationNumber(),
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/registerTeacher")
    public ResponseEntity<UserResponseDTO> registerTeacher(@RequestBody @Valid UserRegistrationDTO dto) {
        if (dto.role() != UserRole.TEACHER) {
            throw new RuntimeException("This endpoint is only for teacher registration.");
        }

        User user = registerUserService.execute(dto);

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                null,
                (user instanceof Teacher t && t.getSubject() != null) ? t.getSubject().getName() : "No subject assigned"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<UserResponseDTO> registerAdmin(@RequestBody @Valid UserRegistrationDTO dto) {
        if (dto.role() != UserRole.ADM) {
            throw new RuntimeException("This endpoint is only for admin registration.");
        }

        User user = registerUserService.execute(dto);

        UserResponseDTO response = new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                null,
                null
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

    @GetMapping("/listByRole")
    public ResponseEntity<List<UserResponseDTO>> listByRole(@RequestParam UserRole role) {
        List<User> users = listUserService.getActiveUsersByRole(role);

        List<UserResponseDTO> response = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        (user instanceof Student s) ? s.getRegistrationNumber() : null,
                        (user instanceof Teacher t && t.getSubject() != null) ? t.getSubject().getName() : null
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/listActive")
    public ResponseEntity<List<UserResponseDTO>> listAllActive() {
        List<User> users = listUserService.getActiveUsers();

        List<UserResponseDTO> response = users.stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        (user instanceof Student s) ? s.getRegistrationNumber() : null,
                        (user instanceof Teacher t && t.getSubject() != null) ? t.getSubject().getName() : null
                ))
                .toList();

        return ResponseEntity.ok(response);
    }
}
