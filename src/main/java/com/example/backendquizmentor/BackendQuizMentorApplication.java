package com.example.backendquizmentor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.backendquizmentor.model")
@ComponentScan(basePackages = "com.example")
//@EnableJpaRepositories(basePackages = "com.example.backendquizmentor.repos")
public class BackendQuizMentorApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendQuizMentorApplication.class, args);
    }

}
