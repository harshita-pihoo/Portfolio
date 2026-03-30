package com.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_users")
@Data
public class AdminUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
}
