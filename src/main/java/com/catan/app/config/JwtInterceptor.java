package com.catan.app.config;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor for JWT tokens
 */
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtConfig jwtConfig;

    /**
     * @param jwtConfig the JWT configuration bean
     */
    public JwtInterceptor(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Intercept requests to verify that the JWT token is valid
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param handler  the handler for the request
     * @return true if the request should be allowed to proceed, false otherwise
     * @throws Exception if an error occurs while processing the request
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            try {
                Jwts.parser().setSigningKey(jwtConfig.getSecretKey()).parseClaimsJws(jwtToken);
                return true;
            } catch (io.jsonwebtoken.JwtException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }
    }
}
