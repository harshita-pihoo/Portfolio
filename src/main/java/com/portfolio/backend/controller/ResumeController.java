package com.portfolio.backend.controller;

import com.portfolio.backend.model.ResumeConfig;
import com.portfolio.backend.repository.ResumeConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeConfigRepository resumeConfigRepository;

    // GET /api/resume — returns current resume URL
    @GetMapping
    public ResponseEntity<?> getResumeUrl() {
        return resumeConfigRepository.findById(1L)
                .map(config -> ResponseEntity.ok(Map.of("url", config.getResumeUrl())))
                .orElse(ResponseEntity.ok(Map.of("url", "")));
    }

    // POST /api/resume/url — saves or updates resume URL (admin only)
    @PostMapping("/url")
    public ResponseEntity<?> updateResumeUrl(@RequestBody Map<String, String> body) {
        String url = body.get("url");

        ResumeConfig config = resumeConfigRepository.findById(1L)
                .orElse(new ResumeConfig());

        config.setId(1L);
        config.setResumeUrl(url);
        resumeConfigRepository.save(config);

        return ResponseEntity.ok(Map.of(
                "message", "Resume URL updated successfully",
                "url", url
        ));
    }
}