package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.model.Enrollment;
import com.secretaria.secretaria.service.enrollment.GenerateEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final GenerateEnrollmentService generateEnrollmentService;

    @GetMapping
    public ResponseEntity<Enrollment> generateEnrollment(){
        return ResponseEntity.ok(generateEnrollmentService.execute());
    }
}
