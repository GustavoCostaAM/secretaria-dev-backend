package com.secretaria.secretaria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be null")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Username must not be null")
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email must not be null")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password must not be null")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Role must not be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, insertable = false, updatable = false)
    private UserRole role;

    public boolean isTeacher() {
        return UserRole.TEACHER.equals(this.role);
    }

    public boolean isStudent() {
        return UserRole.STUDENT.equals(this.role);
    }

    public boolean isAdm(){return UserRole.ADM.equals(this.role);}

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.name()));
    }

    // Spring Security's UserDetails interface methods
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
