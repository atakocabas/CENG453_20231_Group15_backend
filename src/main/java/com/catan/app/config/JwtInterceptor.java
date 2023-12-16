package com.catan.app.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class JwtInterceptor implements HandlerInterceptor {
    private final JwtConfig jwtConfig;

    public JwtInterceptor(JwtConfig jwtConfig) {
        System.out.println("Initializing JwtInterceptor");
        this.jwtConfig = jwtConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            try {
                Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(jwtToken);
                System.out.println("Valid token");
                return true;
            } catch (io.jsonwebtoken.JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                System.out.println("Invalid token");
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            System.out.println("Unauthorized");
            return false;
        }
    }
}
