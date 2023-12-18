package com.example.backendquizmentor.controllers;

import com.example.backendquizmentor.model.Card;
import com.example.backendquizmentor.model.CardDTO;
import com.example.backendquizmentor.model.CustomModule;
import com.example.backendquizmentor.model.ModuleRequestDTO;
import com.example.backendquizmentor.services.ModuleService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
//@RequestMapping("/api/modules")
@Slf4j
@CrossOrigin(origins = "http://localhost:5173/createBlock")
        public class ModuleController {
    private final ModuleService moduleService;

    //private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);


    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

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
