package com.secretaria.secretaria.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.secretaria.secretaria.model.UserRole;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponseDTO(
        Long id,
        String name,
        String username,
        String email,
        UserRole role,
        Long registrationNumber,
        String subjectName
) {}
