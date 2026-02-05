package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.subject.SubjectCreationDTO;
import com.secretaria.secretaria.dto.subject.SubjectResponseDTO;
import com.secretaria.secretaria.model.Subject;
import com.secretaria.secretaria.service.subject.CreateSubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final CreateSubjectService createSubjectService;

    @PostMapping("/create")
    public ResponseEntity<SubjectResponseDTO> createSubject(@RequestBody @Valid SubjectCreationDTO dto){
        Subject subject = createSubjectService.execute(dto);

        SubjectResponseDTO response = new SubjectResponseDTO(
                subject.getId(),
                subject.getName(),
                subject.getTeacher().getId(),
                subject.getTeacher().getName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
