package com.secretaria.secretaria.service.grades;

import com.secretaria.secretaria.dto.grades.SendGradesDTO;
import com.secretaria.secretaria.model.Assessment;
import com.secretaria.secretaria.model.Student;
import com.secretaria.secretaria.model.Subject;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.repository.AssessmentRepository;
import com.secretaria.secretaria.repository.StudentRepository;
import com.secretaria.secretaria.repository.SubjectRepository;
import com.secretaria.secretaria.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UpdateGradesService {
    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public UpdateGradesService(AssessmentRepository assessmentRepository, StudentRepository studentRepository,
                               TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public Assessment UpdateAssesment(SendGradesDTO gradesDTO){
        //first we check if the student is registered
        Student student = getUser(gradesDTO.getStudentId().longValue());
        if (student == null){
            return null;
        }

        //then we check if the teacher is registered
        Teacher teacher = getTeacher(gradesDTO.getTeacherId());
        if (teacher == null){
            return null;
        }

        //from here, the teacher and the students are ok
        //then we check if the Assessment exists
        Assessment fetched = assessmentRepository.findAssessmentById(gradesDTO.getAssessmentId());
        if (fetched == null){
            return null;
        }

        //filling the assessment fields

        if (!fetched.getGrade().equals(gradesDTO.getGrade())){
            fetched.setGrade(gradesDTO.getGrade());
        }
        if (!fetched.getObservations().equals(gradesDTO.getObservations())){
            fetched.setObservations(gradesDTO.getObservations());
        }
        if (!fetched.getSubject().getId().equals(gradesDTO.getSubjectId().longValue())){
            //fetches the new subject
            Subject newSubject = subjectRepository.getSubjectsById(gradesDTO.getSubjectId().longValue());
            if (newSubject == null){
                return null;
            }

            fetched.setSubject(newSubject);
        }
        if (!fetched.getStudent().getId().equals(gradesDTO.getStudentId().longValue())){
            //fetches the new student
            Student newStudent = studentRepository.getStudentById(gradesDTO.getStudentId().longValue());
            if (newStudent == null){
                return null;
            }

            fetched.setStudent(newStudent);
        }

        //saving the new Assessemnt
        return assessmentRepository.save(fetched);
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
