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

        // ✅ FIX: use getRequestURI instead of getServletPath
        String path = request.getRequestURI();

        // 🔥 Skip JWT for public endpoints
        if (path.startsWith("/api/auth") || path.startsWith("/api/projects")) {
            filterChain.doFilter(request, response);
            return;
        }

        // ✅ Get Authorization header
        String authHeader = request.getHeader("Authorization");

        // ✅ If no token → continue (don't block)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // ✅ Extract token
            String token = authHeader.substring(7);

            // ✅ Validate token
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
            // ❗ Don't break request on invalid token
            System.out.println("JWT Error: " + e.getMessage());
        }

        // ✅ Continue filter chain
        filterChain.doFilter(request, response);
    }
}