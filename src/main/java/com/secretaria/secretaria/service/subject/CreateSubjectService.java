package com.secretaria.secretaria.service.subject;

import com.secretaria.secretaria.dto.subject.SubjectCreationDTO;
import com.secretaria.secretaria.model.Subject;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.repository.SubjectRepository;
import com.secretaria.secretaria.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository  teacherRepository;

    @Transactional
    public Subject execute(SubjectCreationDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found with ID: " + dto.teacherId()));

        if(subjectRepository.existsByTeacher(teacher)){
            throw new RuntimeException("Teacher already has a subject");
        }

        Subject newSubject = Subject.builder()
                .name(dto.name())
                .teacher(teacher)
                .build();

        return subjectRepository.save(newSubject);
    }
}
