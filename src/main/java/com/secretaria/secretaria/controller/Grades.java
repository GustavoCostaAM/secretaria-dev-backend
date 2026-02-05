package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.grades.SendGradesDTO;
import com.secretaria.secretaria.dto.grades.SendGradesResponseDTO;
import com.secretaria.secretaria.model.Assessment;
import com.secretaria.secretaria.service.grades.GradesService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/grades")
public class Grades {
    private final GradesService gradesService;

    public Grades(GradesService gradesService) {
        this.gradesService = gradesService;
    }

    @PostMapping("/sendGrades")
    public ResponseEntity<?> InsertGrades(@RequestBody SendGradesDTO gradesDTO){
        Assessment generated = null;
        String messageError = "";
        int status = 201;
        SendGradesResponseDTO response;

        //error validations
        try {
            generated = gradesService.AddAssesment(gradesDTO);

        }catch (DataIntegrityViolationException exception){
            exception.printStackTrace();
            messageError = "FAILED TO INSERT DATA BY DATA INTEGRITY";
            status = 400;
            System.out.println(messageError);

        }catch (ConstraintViolationException exception){
            exception.printStackTrace();
            messageError = "FAILED TO INSERT DATA BY DATA VIOLATION";
            status = 400;
            System.out.println(messageError);

        }catch (PersistenceException exception){
            exception.printStackTrace();
            messageError = "FAILED TO INSERT DATA BY PERSISTENCE ERROR";
            status = 503;
            System.out.println(messageError);

        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
            messageError = "FAILED TO INSERT DATA BY ARGUMENT EXCEPTION";
            status = 400;
            System.out.println(messageError);

        }catch (Exception exception){
            exception.printStackTrace();
            messageError = "UNKNOWN ERROR";
            status = 500;
            System.out.println(messageError);
        }

        if (status != 201){
            return ResponseEntity.status(status).body(messageError);
        }

        response = SendGradesResponseDTO.builder()
                .grade(generated.getGrade())
                .date(generated.getDate())
                .observation(generated.getObservations())
                .studentName(generated.student.getName())
                .subjectName(generated.subject.getName())
                .build();

        //from here the assessment have been created successfully
        return ResponseEntity.status(status).body(response);
    }
}
