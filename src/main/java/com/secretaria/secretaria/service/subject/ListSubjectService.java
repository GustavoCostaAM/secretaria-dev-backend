package com.secretaria.secretaria.service.subject;

import com.secretaria.secretaria.dto.subject.SubjectResponseDTO;
import com.secretaria.secretaria.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListSubjectService {
    private final SubjectRepository subjectRepository;

    public List<SubjectResponseDTO> execute() {
        return subjectRepository.findAll().stream()
                .map(subject -> new SubjectResponseDTO(
                        subject.getId(),
                        subject.getName(),
                        subject.getTeacher() != null ? subject.getTeacher().getId() : null,
                        subject.getTeacher() != null ? subject.getTeacher().getName() : null
                ))
                .collect(Collectors.toList());
    }
}
