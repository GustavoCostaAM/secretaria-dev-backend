package com.secretaria.secretaria.service.grades;

import com.secretaria.secretaria.dto.grades.GetGradesResponseDTO;
import com.secretaria.secretaria.model.Assessment;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.model.UserRole;
import com.secretaria.secretaria.repository.AssessmentRepository;
import com.secretaria.secretaria.util.JSON;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GetBoletimService {
    private static final double APPROVAL_THRESHOLD = 7.0;

    private final AssessmentRepository assessmentRepository;

    public GetBoletimService(AssessmentRepository assessmentRepository) {
        this.assessmentRepository = assessmentRepository;
    }

    public JSON<?> doFilter(User user, Optional<JSON<String>> doFilter){
        UserRole role = user.getRole();
        JSON<String> filter = doFilter.orElse(null);

        //no filter cases
        if (filter == null){
            if (role == UserRole.STUDENT){
                List<Assessment> assessments = assessmentRepository.findAllByStudent_Id(user.getId());
                return buildStudentResponse(assessments);

            }else{
                //nesse caso filtramos todos os alunos
                List<Assessment> assessments = assessmentRepository.findAll();

                return buildTeacherResponse(assessments);
            }
        }

        //agora carregamos a base de dados para filtrar
        Map<String, String> mappedFilter = filter.toMap();
        List<Assessment> assessments;
        if (role == UserRole.STUDENT) {
            assessments = assessmentRepository.findAllByStudent_Id(user.getId());
        } else {
            assessments = assessmentRepository.findAll();
        }

        //aplica os filtros de forma dinamica
        List<Assessment> filtred = assessments.stream()
                .filter(a -> {

                    //filtro por disciplina
                    if (mappedFilter.containsKey("subject")){
                        if (!a.getSubject().getName().equalsIgnoreCase(mappedFilter.get("subject"))){
                            return false;
                        }
                    }

                    // filtro por aluno
                    if (mappedFilter.containsKey("student")) {
                        if (!a.getStudent().getName()
                                .equalsIgnoreCase(mappedFilter.get("student"))) {
                            return false;
                        }
                    }

                    // filtro por ID do aluno
                    if (mappedFilter.containsKey("studentId")) {
                        try {
                            Long studentId = Long.parseLong(mappedFilter.get("studentId"));
                            if (!a.getStudent().getId().equals(studentId)) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            // invalid studentId value: ignore this filter
                        }
                    }

                    // filtro por nota mínima
                    if (mappedFilter.containsKey("minGrade")) {
                        try {
                            double min = Double.parseDouble(mappedFilter.get("minGrade"));
                            if (a.getGrade() < min) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            // invalid minGrade value: ignore this filter
                        }
                    }

                    // filtro por nota máxima
                    if (mappedFilter.containsKey("maxGrade")) {
                        try {
                            double max = Double.parseDouble(mappedFilter.get("maxGrade"));
                            if (a.getGrade() > max) {
                                return false;
                            }
                        } catch (NumberFormatException e) {
                            // invalid maxGrade value: ignore this filter
                        }
                    }

                    return true;

                })
                .toList();

        //build da resposta
        if (role == UserRole.STUDENT){
            return buildStudentResponse(filtred);
        }
        return buildTeacherResponse(filtred);
    }

    private JSON<GetGradesResponseDTO> buildStudentResponse(List<Assessment> Listassessments){
        //agrupa os assessments
        Map<String, List<Assessment>> group = Listassessments.stream()
                .collect(Collectors.groupingBy(a -> a.getSubject().getId() + "|" + a.getStudent().getId())
                );

        JSON<GetGradesResponseDTO> response = new JSON<>();
        for(Map.Entry<String, List<Assessment>> element : group.entrySet()){
            List<Assessment> assessments = element.getValue();

            String subject = assessments.get(0).getSubject().getName();
            double nota1 = assessments.get(0).getGrade();
            double nota2 = assessments.size() > 1 ? assessments.get(1).getGrade() : 0;
            double average = assessments.size() > 1 ? (nota1 + nota2) / 2 : nota1;
            boolean approved = average >= APPROVAL_THRESHOLD;

            GetGradesResponseDTO responseDTO = GetGradesResponseDTO.builder()
                    .disciplina(subject)
                    .nota1(nota1)
                    .nota2(nota2)
                    .media(average)
                    .aprovado(approved)
                    .build();

            response.addValue(subject, responseDTO);
        }

        return response;
    }

    private JSON<?> buildTeacherResponse(List<Assessment> Listassessments){
        Map<String, List<Assessment>> group = Listassessments.stream()
                .collect(Collectors.groupingBy(a -> a.getStudent().getId() + "|" + a.getSubject().getId())
                );

        JSON<JSON<GetGradesResponseDTO>> response = new JSON<>();
        for(Map.Entry<String, List<Assessment>> element : group.entrySet()){

            List<Assessment> assessments = element.getValue();
            String subject = assessments.get(0).getSubject().getName();
            double nota1 = assessments.get(0).getGrade();
            double nota2 = assessments.size() > 1 ? assessments.get(1).getGrade() : 0;
            double average = assessments.size() > 1 ? (nota1 + nota2) / 2 : nota1;
            boolean approved = average >= APPROVAL_THRESHOLD;
            String studentName = assessments.get(0).getStudent().getName();

            // Monta as observações separadas por uma linha
            String observations;
            StringBuilder obsBuilder = new StringBuilder();

            // Observação da nota1
            String obs1 = assessments.get(0).getObservations();
            if (obs1 != null && !obs1.isBlank()) {
                obsBuilder.append(obs1);
            }

            // Se houver nota2, adiciona separador e observação da nota2
            if (assessments.size() > 1) {
                if (!obsBuilder.isEmpty()) {
                    obsBuilder.append("\n---\n");
                }
                String obs2 = assessments.get(1).getObservations();
                if (obs2 != null && !obs2.isBlank()) {
                    obsBuilder.append(obs2);
                }
            }

            observations = !obsBuilder.isEmpty() ? obsBuilder.toString() : "Nenhuma observação";

            GetGradesResponseDTO responseDTO = GetGradesResponseDTO.builder()
                    .disciplina(subject)
                    .nota1(nota1)
                    .nota2(nota2)
                    .media(average)
                    .aprovado(approved)
                    .observations(observations)
                    .build();

            //valida se ja tem esse aluno na response
            JSON<GetGradesResponseDTO> studentJSON = response.getMap().get(studentName);
            if (studentJSON == null){
                studentJSON = new JSON<>();
                response.addValue(studentName, studentJSON);
            }

            studentJSON.addValue(subject, responseDTO);
        }

        return response;
    }
}
