package com.example.backendquizmentor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.slf4j.LoggerFactory;

@Configuration
public class CorsConfig {
    private static final Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                logger.debug("Configuring CORS");
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("Content-Type", "Authorization")
                        .allowCredentials(true);

                registry.addMapping("/logout")
                        .allowedOrigins("http://localhost:5173")
                        .exposedHeaders("Content-Type", "Authorization")
                        .allowCredentials(true);
            }
        };
    }
}