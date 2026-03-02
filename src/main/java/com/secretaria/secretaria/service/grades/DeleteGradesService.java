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

@Service
public class DeleteGradesService {
    private final AssessmentRepository assessmentRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;

    public DeleteGradesService(AssessmentRepository assessmentRepository, StudentRepository studentRepository,
                               TeacherRepository teacherRepository, SubjectRepository subjectRepository) {
        this.assessmentRepository = assessmentRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
    }

    public boolean DeleteAssesment(SendGradesDTO gradesDTO, Teacher teacher){
        //first we check if the student is registered
        Student student = getUser(gradesDTO.getStudentId().longValue());
        if (student == null){
            return false;
        }

        //remember: the teacher is already validated
        //now we have to load the teacher's subject using the authenticated teacher's id
        Subject subject = getSubject(gradesDTO.getSubjectId(), teacher.getId().intValue());
        if(subject == null){
            return false;
        }

        //now we fetch the assessment and validate the infos
        Assessment assessment = getAssessment(gradesDTO.getAssessmentId().intValue());

        if (assessment == null){
            return false;
        }

        if (gradesDTO.getStudentId().longValue() != assessment.getStudent().getId()){
            return false;
        }

        if (teacher.getId().longValue() != assessment.getSubject().getTeacher().getId().longValue()){
            return false;
        }

        assessmentRepository.deleteAssessmentById(assessment.getId());
        return true;
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

    private Subject getSubject(Integer subjectId, Integer teacherId){
        Subject subject = subjectRepository.findByIdAndTeacher_Id(subjectId.longValue(), teacherId.longValue());

        //we can add any validation here

        return subject;
    }

    private Assessment getAssessment(Integer assessmentId){
        return assessmentRepository.findAssessmentById(assessmentId.longValue());
    }
}
