package com.secretaria.secretaria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{
    @Column(unique = true)
    private Long registrationNumber;

    @OneToMany(mappedBy = "student")
    private List<Assessment> assessments;
}
