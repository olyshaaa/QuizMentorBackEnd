package com.example.backendquizmentor.controllers;

import com.example.backendquizmentor.model.UserRequestDTO;
import com.example.backendquizmentor.roles.UserRole;
import com.example.backendquizmentor.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin(origins = {"https://quiz-mentor.vercel.app/signup", "https://quiz-mentor.vercel.app/login", "https://quiz-mentor.vercel.app"})
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/newuser")
    public ResponseEntity<String> update(@RequestBody UserRequestDTO userRequest,
                                         Model model){
        logger.info("Receiver request to create a new user with login: {}", userRequest.getLogin());
        System.out.println("Controller AuthController");
        String passHash = passwordEncoder.encode(userRequest.getPassword());
        if(userService.isUsernameTaken(userRequest.getLogin())){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(!userService.addUser(userRequest.getLogin(), passHash, UserRole.USER, userRequest.getEmail())){
            model.addAttribute("exists", true);
            model.addAttribute("login", userRequest.getLogin());
            return ResponseEntity.badRequest().body("User registration failed");
        }
        logger.info("created new user");
        return ResponseEntity.ok().build();
    }

}