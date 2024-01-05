package com.example.backendquizmentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan("com.example.backendquizmentor.model")
@ComponentScan(basePackages = "com.example")
public class BackendQuizMentorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendQuizMentorApplication.class, args);
    }

}
