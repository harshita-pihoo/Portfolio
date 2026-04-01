package com.portfolio.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private String resumeUrl = "";

    // ✅ Save resume URL
    @PostMapping("/url")
    public Map<String, String> saveResume(@RequestBody Map<String, String> body) {
        resumeUrl = body.get("url");

        Map<String, String> response = new HashMap<>();
        response.put("url", resumeUrl);

        return response;
    }

    // ✅ Get resume URL
    @GetMapping
    public Map<String, String> getResume() {
        Map<String, String> response = new HashMap<>();
        response.put("url", resumeUrl);
        return response;
    }

    @GetMapping("/test")
    public String test() {
        return "Resume controller working";
    }
}