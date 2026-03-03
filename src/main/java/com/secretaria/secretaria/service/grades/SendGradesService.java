package com.secretaria.secretaria.service.grades;

import com.secretaria.secretaria.dto.grades.SendGradesDTO;
import com.secretaria.secretaria.model.*;
import com.secretaria.secretaria.repository.AssessmentRepository;
import com.secretaria.secretaria.repository.StudentRepository;
import com.secretaria.secretaria.repository.SubjectRepository;
import com.secretaria.secretaria.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SendGradesService {
    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public SendGradesService(AssessmentRepository assessmentRepository, StudentRepository studentRepository,
                             TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public Assessment AddAssesment(SendGradesDTO gradesDTO, Teacher teacher){
        //first we check if the student is registered
        Student student = getUser(gradesDTO.getStudentId());
        if (student == null){
            return null;
        }

        //remember: the teacher is already validated by the filterchain
        //pegamos a disciplina do professor (ja autenticado)
        Subject subject = teacher.getSubject();

        if (subject == null){
            return null;
        }

        //adiciona observação default caso precise
        String observations = gradesDTO.getObservations() != null ? gradesDTO.getObservations() : "Sem observações";

        //Creating the assessment
        Assessment newAssessment = Assessment.builder()
                .grade(gradesDTO.getGrade())
                .date(LocalDate.now())
                .subject(subject)
                .observations(gradesDTO.getObservations())
                .student(student)
                .build();

        return assessmentRepository.save(newAssessment);
    }

    //privates methods
    private Student getUser(Long studentId){
        Student student = studentRepository.getStudentById(studentId);

        //we can add any validation here

        return student;
    }
}
