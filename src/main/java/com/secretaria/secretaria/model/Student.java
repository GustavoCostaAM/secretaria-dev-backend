package com.secretaria.secretaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "STUDENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends User{
    @Column(name = "registration_number")
    private Long registrationNumber;

    @OneToMany(mappedBy = "student")
    private List<Assessment> assessments = new ArrayList<>();
}
