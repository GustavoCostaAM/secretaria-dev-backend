package com.secretaria.secretaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assessments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Column
    private String observations;

    @NotNull(message = "Grade must not be null")
    @Column(nullable = false)
    private Integer grade;
}
