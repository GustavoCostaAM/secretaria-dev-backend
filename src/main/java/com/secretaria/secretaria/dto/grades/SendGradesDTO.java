package com.secretaria.secretaria.dto.grades;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SendGradesDTO {
    private final Long assessmentId;
    private final Double grade;
    private final Integer studentId;
    private final Integer teacherId;
    private final Integer subjectId;
    private final String observations;
}
