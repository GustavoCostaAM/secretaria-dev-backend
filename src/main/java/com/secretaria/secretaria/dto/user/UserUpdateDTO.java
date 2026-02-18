package com.secretaria.secretaria.dto.user;

public record UserUpdateDTO(
        String name,
        String email,
        Long registrationNumber
) {}
