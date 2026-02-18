package com.secretaria.secretaria.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("ADM")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Admin extends User {

}
