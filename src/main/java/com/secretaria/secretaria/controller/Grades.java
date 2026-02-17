package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.grades.SendGradesDTO;
import com.secretaria.secretaria.dto.grades.SendGradesResponseDTO;
import com.secretaria.secretaria.model.Assessment;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.service.grades.DeleteGradesService;
import com.secretaria.secretaria.service.grades.SendGradesService;
import com.secretaria.secretaria.service.grades.UpdateGradesService;
import com.secretaria.secretaria.util.JSON;
import jakarta.persistence.PersistenceException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/grades")
public class Grades {
    private final SendGradesService sendgradesService;
    private final UpdateGradesService updateGradesService;
    private final DeleteGradesService deleteGradesService;

    public Grades(SendGradesService sendgradesService, UpdateGradesService updateGradesService, DeleteGradesService deleteGradesService) {
        this.sendgradesService = sendgradesService;
        this.updateGradesService = updateGradesService;
        this.deleteGradesService = deleteGradesService;
    }

    @PostMapping("/sendGrades")
    public ResponseEntity<?> InsertGrades(@RequestBody SendGradesDTO gradesDTO, Authentication authentication){
        Assessment generated = null;
        String messageError = "";
        int status = 201;
        SendGradesResponseDTO response;
        Teacher teacher = (Teacher) authentication.getPrincipal();

        //error validations
        try {
            generated = sendgradesService.AddAssesment(gradesDTO, teacher);

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

        if (generated == null){
            status = 400;
            messageError = "REQUEST BODY IS INVALID";
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

    @PostMapping("/updateGrades")
    public ResponseEntity<?> UpdateGrades(@RequestBody SendGradesDTO gradesDTO, Authentication authentication){
        Assessment updated = null;
        String messageError = "";
        int status = 201;
        SendGradesResponseDTO response;
        Teacher teacher = (Teacher) authentication.getPrincipal();

        //error validations
        try {
            updated = updateGradesService.UpdateAssesment(gradesDTO, teacher);

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

        if (updated == null){
            status = 400;
            messageError = "REQUEST BODY IS INVALID";
            System.out.println(messageError);
        }

        if (status != 201){
            return ResponseEntity.status(status).body(messageError);
        }

        response = SendGradesResponseDTO.builder()
                .id(updated.getId())
                .grade(updated.getGrade())
                .date(updated.getDate())
                .observation(updated.getObservations())
                .studentName(updated.student.getName())
                .subjectName(updated.subject.getName())
                .build();

        return ResponseEntity.status(status).body(response);
    }

    @PostMapping("/deleteGrades")
    public ResponseEntity<?> deleteGrades(@RequestBody SendGradesDTO gradesDTO, Authentication authentication){
        boolean deleted = false;
        String messageError = "";
        int status = 200;
        JSON<String> response = new JSON<>();
        Teacher teacher = (Teacher) authentication.getPrincipal();

        try {
            deleted = deleteGradesService.DeleteAssesment(gradesDTO, teacher);
        }catch (DataIntegrityViolationException exception){
            exception.printStackTrace();
            messageError = "FAILED TO DELETE DATA BY DATA INTEGRITY";
            status = 400;
            System.out.println(messageError);

        }catch (ConstraintViolationException exception){
            exception.printStackTrace();
            messageError = "FAILED TO DELETE DATA BY DATA VIOLATION";
            status = 400;
            System.out.println(messageError);

        }catch (PersistenceException exception){
            exception.printStackTrace();
            messageError = "FAILED TO DELETE DATA BY PERSISTENCE ERROR";
            status = 503;
            System.out.println(messageError);

        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
            messageError = "FAILED TO DELETE DATA BY ARGUMENT EXCEPTION";
            status = 400;
            System.out.println(messageError);

        }catch (Exception exception){
            exception.printStackTrace();
            messageError = "UNKNOWN ERROR";
            status = 500;
            System.out.println(messageError);
        }

        if (!deleted){
            status = 400;
            messageError = "REQUEST BODY IS INVALID OR ASSESSMENT NOT FOUND";
            System.out.println(messageError);
        }

        if (status != 200){
            response.addValue("error", messageError);
        }

        return ResponseEntity.status(status).body(response.map());
    }
}
