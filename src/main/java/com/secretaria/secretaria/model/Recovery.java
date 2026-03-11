package com.secretaria.secretaria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recoveries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recovery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 10)
    private String code;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiration;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecoveryStatuses status;
}
