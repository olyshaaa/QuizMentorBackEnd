package com.example.backendquizmentor.controllers;

import com.example.backendquizmentor.model.*;
import com.example.backendquizmentor.repos.ModuleRepository;
import com.example.backendquizmentor.repos.UserRepository;
import com.example.backendquizmentor.services.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@Slf4j
@CrossOrigin(origins = {"https://quiz-mentor.vercel.app", "https://quiz-mentor.vercel.app/home"})
        public class ModuleController {
    private final ModuleService moduleService;

    @Autowired
    private ModuleRepository moduleRepository;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }
    @CrossOrigin(origins = {"https://quiz-mentor.vercel.app", "hhttps://quiz-mentor.vercel.app/home"})
    @PostMapping(value = "/createModule")
    @Transactional
    public ResponseEntity<String> createModule(@RequestBody ModuleRequestDTO moduleRequest){
        if (log.isDebugEnabled()) {
            log.info("Debug message");
        }
        log.info("Received request to create a new module with title: {}", moduleRequest.getModuleName());
        CustomModule module_custom = new CustomModule();
        log.info("CustomModule name, " + moduleRequest.getAuthorUsername());
        module_custom.setModuleName(moduleRequest.getModuleName());
            List<CardDTO> cardDTOs = moduleRequest.getCards();
        List<Card> cards = cardDTOs.stream()
                .map(cardDTO -> {
                    Card card = new Card();
                    card.setTerm(cardDTO.getTerm());
                    log.info(cardDTO.getTerm());
                    card.setDefinition(cardDTO.getDefinition());
                    log.info(cardDTO.getDefinition());
                    card.setCustomModule(module_custom);
                    return card;
                })
                .collect(Collectors.toList());

        module_custom.setCards(cards);
        module_custom.setAuthorUsername(moduleRequest.getAuthorUsername());
            moduleService.saveModule(module_custom);
            return ResponseEntity.ok().build();

    }



}
