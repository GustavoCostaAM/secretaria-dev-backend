package com.secretaria.secretaria.dto.user;

import com.secretaria.secretaria.model.UserRole;

public record UserUpdateDTO(
        String name,
        String email,
        String username
) {}
