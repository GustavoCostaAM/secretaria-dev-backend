package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.subject.SubjectCreationDTO;
import com.secretaria.secretaria.dto.subject.SubjectResponseDTO;
import com.secretaria.secretaria.dto.subject.SubjectUpdateDTO;
import com.secretaria.secretaria.model.Subject;
import com.secretaria.secretaria.service.subject.CreateSubjectService;
import com.secretaria.secretaria.service.subject.UpdateSubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final CreateSubjectService createSubjectService;
    private final UpdateSubjectService updateSubjectService;

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

    @PutMapping("/{id}")
    public ResponseEntity<SubjectResponseDTO> update(@PathVariable Long id, @RequestBody SubjectUpdateDTO dto) {
        Subject updatedSubject = updateSubjectService.execute(id, dto);

        SubjectResponseDTO response = new SubjectResponseDTO(
                updatedSubject.getId(),
                updatedSubject.getName(),
                updatedSubject.getTeacher().getId(),
                updatedSubject.getTeacher().getName()
        );

        return ResponseEntity.ok(response);
    }
}
