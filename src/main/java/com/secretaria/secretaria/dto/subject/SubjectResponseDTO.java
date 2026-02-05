package com.secretaria.secretaria.dto.subject;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SubjectResponseDTO(
        Long id,
        String name,
        Long teacherId,
        String teacherName
) {}
