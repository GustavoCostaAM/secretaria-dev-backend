package com.secretaria.secretaria.dto.user;

import com.secretaria.secretaria.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationDTO(
        @NotBlank String name,
        @NotBlank String username,
        @Email String email,
        @NotBlank String password,
        @NotNull UserRole role,
        Integer enrollmentCode,
        Long subjectId
) {}
