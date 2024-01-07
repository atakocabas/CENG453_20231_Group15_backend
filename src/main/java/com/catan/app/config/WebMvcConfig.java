package com.catan.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for JWT tokens
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtConfig jwtConfig;

    /**
     * @param jwtConfig the JWT configuration bean
     */
    public WebMvcConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * @return the JWT interceptor
     */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(jwtConfig);
    }


    /**
     * Add the JWT interceptor to the interceptor registry
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/user/**")
                .excludePathPatterns(("/error"));
    }
}