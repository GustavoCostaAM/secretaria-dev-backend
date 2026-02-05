package com.secretaria.secretaria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubjectCreationDTO(
    @NotBlank String name,
    @NotNull Long teacherId
) {}
