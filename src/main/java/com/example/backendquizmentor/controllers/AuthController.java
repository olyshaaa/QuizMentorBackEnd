package com.example.backendquizmentor.controllers;

import com.example.backendquizmentor.model.UserRequestDTO;
import com.example.backendquizmentor.roles.UserRole;
import com.example.backendquizmentor.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@CrossOrigin(origins = {"http://localhost:5173/signup", "http://localhost:5173/login", "http://localhost:5173"})
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
        String passHash = passwordEncoder.encode(userRequest.getPassword());

        if(!userService.addUser(userRequest.getLogin(), passHash, UserRole.USER, userRequest.getEmail())){
            model.addAttribute("exists", true);
            model.addAttribute("login", userRequest.getLogin());
            return ResponseEntity.badRequest().body("User registration failed");
        }

        return ResponseEntity.ok().build();
    }


}
