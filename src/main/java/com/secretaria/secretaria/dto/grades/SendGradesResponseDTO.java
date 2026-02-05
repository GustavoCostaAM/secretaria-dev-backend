package com.secretaria.secretaria.dto.grades;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendGradesResponseDTO {
    private Double grade;
    private LocalDate date;
    private String observation;
    private String subjectName;
    private String studentName;
}
