package com.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "resume_config")
@Data
public class ResumeConfig {

    @Id
    private Long id;

    @Column(name = "resume_url", length = 1000)
    private String resumeUrl;
}