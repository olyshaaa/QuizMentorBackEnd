package com.example.backendquizmentor.controllers;

import com.example.backendquizmentor.model.*;
import com.example.backendquizmentor.repos.ModuleRepository;
import com.example.backendquizmentor.repos.UserRepository;
import com.example.backendquizmentor.services.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ModuleREstController {
    private static final Logger logger = LoggerFactory.getLogger(ModuleREstController.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleService moduleService;
    @CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5173/home"})
    @GetMapping("modules/{username}")
    public ResponseEntity<List<ModuleRequestDTO>> getModulesByUsername(@PathVariable("username") String username){
        List<ModuleRequestDTO> modules = moduleService.getModulesByUsername(username);
        if(modules.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.info("modules are got");
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }
}
