package com.secretaria.secretaria.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer code;

    @Column(nullable = false)
    private Boolean activeStudent = false;
}
