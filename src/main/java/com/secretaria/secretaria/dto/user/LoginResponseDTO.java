package com.secretaria.secretaria.dto.user;

import com.secretaria.secretaria.model.UserRole;

public record LoginResponseDTO(
        Long id,
        String name,
        String username,
        UserRole role,
        String token,
        boolean active
) {}
