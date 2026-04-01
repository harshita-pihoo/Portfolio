package com.portfolio.backend.repository;

import com.portfolio.backend.model.ResumeConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeConfigRepository extends JpaRepository<ResumeConfig, Long> {
}