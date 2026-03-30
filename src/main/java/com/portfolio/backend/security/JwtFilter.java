package com.portfolio.backend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        // ✅ 1. ALWAYS allow these endpoints
        if (path.equals("/api/auth/login") ||
                path.startsWith("/api/projects") ||
                path.startsWith("/api/auth")) {

            filterChain.doFilter(request, response);
            return;
        }

        // ✅ 2. Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // ✅ 3. If no token → just continue (don't block)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // ✅ 4. Extract token
            String token = authHeader.substring(7);

            // ✅ 5. Validate token
            if (jwtService.isTokenValid(token)) {
                String username = jwtService.extractUsername(token);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of()
                        );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (Exception e) {
            // ❗ Important: don't crash request on bad token
            System.out.println("JWT Error: " + e.getMessage());
        }

        // ✅ 6. Continue filter chain
        filterChain.doFilter(request, response);
    }
}