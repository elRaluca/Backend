package com.example.backend.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="recoveraccount")

public class RecoverAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idRecoverAccount;
    private String resetCode;
    @Column(nullable = false)
    private LocalDateTime expirationTime;
    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id",nullable = false)
    private OurUsers user;
    private int failedAttempts;
    private LocalDateTime lockTime;

}
