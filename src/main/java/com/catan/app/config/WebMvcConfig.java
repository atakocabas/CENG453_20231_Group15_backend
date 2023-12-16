package com.catan.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final JwtConfig jwtConfig;

    public WebMvcConfig(JwtConfig jwtConfig) {
        System.out.println("Initializing WebMvcConfig");
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(jwtConfig);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("Adding interceptor");
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/user/**")
                .excludePathPatterns(("/error"));
    }
}