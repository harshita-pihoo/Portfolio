package com.portfolio.backend.controller;

import com.portfolio.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");

        // ✅ TEMPORARY LOGIN (NO DATABASE)
        if ("admin".equals(username) && "admin123".equals(password)) {
            String token = jwtService.generateToken(username);
            return ResponseEntity.ok(Map.of("token", token, "expiresIn", 86400000));
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
    }
}