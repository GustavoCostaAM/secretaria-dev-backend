package com.secretaria.secretaria.service.subject;

import com.secretaria.secretaria.dto.subject.SubjectUpdateDTO;
import com.secretaria.secretaria.model.Subject;
import com.secretaria.secretaria.model.Teacher;
import com.secretaria.secretaria.repository.SubjectRepository;
import com.secretaria.secretaria.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @Transactional
    public Subject execute(Long id, SubjectUpdateDTO dto) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (dto.name() != null) subject.setName(dto.name());

        if (dto.teacherId() != null) {
            Teacher teacher = (Teacher) userRepository.findById(dto.teacherId())
                    .filter(u -> u instanceof Teacher && u.isActive())
                    .orElseThrow(() -> new RuntimeException("Teacher not found or inactive"));

            if (subjectRepository.existsByTeacherAndIdNot(teacher, id)) {
                throw new RuntimeException("This teacher is already assigned to another subject.");
            }

            subject.setTeacher(teacher);
        }

        return subjectRepository.save(subject);
    }
}
