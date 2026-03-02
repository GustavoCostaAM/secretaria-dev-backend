package com.secretaria.secretaria.dto.grades;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendGradesDTO {
    private final Long assessmentId;

    @Min(value = 0, message = "Grade must be at least 0")
    @Max(value = 10, message = "Grade must be at most 10")
    private final Double grade;

    private final Long studentId;

    private final Integer subjectId;

    private final Integer teacherId;

    private final String observations;
}
