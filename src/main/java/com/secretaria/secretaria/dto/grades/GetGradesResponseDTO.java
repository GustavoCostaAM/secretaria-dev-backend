package com.secretaria.secretaria.dto.grades;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetGradesResponseDTO {
    private String disciplina;
    private double nota1;
    private double nota2;
    private double media;
    private boolean aprovado;
}
