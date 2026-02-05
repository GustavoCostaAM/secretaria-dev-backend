package com.secretaria.secretaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    public Subject subject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    public Student student;

    @NotNull(message = "Grade must not be null")
    @Column(nullable = false)
    private Double grade;

    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @Column
    private String observations;
}
