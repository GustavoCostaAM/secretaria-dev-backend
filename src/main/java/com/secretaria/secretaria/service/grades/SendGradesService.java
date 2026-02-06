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

    public Assessment AddAssesment(SendGradesDTO gradesDTO){
        //first we check if the student is registred
        Student student = getUser(gradesDTO.getStudentId().longValue());
        if (student == null){
            return null;
        }

        //then we check if the teacher is registred
        Teacher teacher = getTeacher(gradesDTO.getTeacherId());
        if (teacher == null){
            return null;
        }

        //from here, the teacher and the students are ok
        //check if the subject exists
        Subject subject = getSubject(gradesDTO.getSubjectId());

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

    private Teacher getTeacher(Integer teacherId){
        Teacher teacher = teacherRepository.getTeachersById(teacherId.longValue());

        //we can add any validation here

        return teacher;
    }

    private Subject getSubject(Integer subjectId){
        Subject subject = subjectRepository.getSubjectsById(subjectId.longValue());

        //we can add any validation here

        return subject;
    }
}
