package com.example.backendquizmentor.services;

import com.example.backendquizmentor.model.Card;
import com.example.backendquizmentor.model.CardDTO;
import com.example.backendquizmentor.model.CustomModule;
import com.example.backendquizmentor.model.ModuleRequestDTO;
import com.example.backendquizmentor.repos.CardRepository;
import com.example.backendquizmentor.repos.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {
    private final ModuleRepository moduleRepository;

    private final CardRepository cardRepository;
    private final Logger logger = LoggerFactory.getLogger(ModuleService.class);

    public ModuleService(ModuleRepository moduleRepository, CardRepository cardRepository) {
        this.moduleRepository = moduleRepository;
        this.cardRepository = cardRepository;
    }
    @Transactional
    public boolean createModule(String moduleName, List<CardDTO> cards, String authorUsername){
        logger.info("Creating a new module with name: {}, author: {}", moduleName, authorUsername);

        try {
            CustomModule module = new CustomModule(moduleName, authorUsername);
            for (CardDTO carddto : cards) {
                Card card = new Card(carddto.getTerm(), carddto.getDefinition(), module);
                module.addCard(card);
            }
            moduleRepository.save(module);

            logger.info("Transaction status after save: {}", TransactionSynchronizationManager.isActualTransactionActive());
        } catch (Exception e) {
            logger.error("Error during module creation", e);
        }

        logger.info("Transaction status at the end of createModule method: {}", TransactionSynchronizationManager.isActualTransactionActive());

        return true;
    }
    @Transactional
    public void saveModule(CustomModule module) {
        System.out.println("Creating a new module");
        try {
            moduleRepository.save(module);
            for (Card card: module.getCards()){
                card.setCustomModule(module);
                cardRepository.save(card);
                System.out.println("Added card: " + card.getTerm());
            }
            logger.info("Module saved");
        } catch (Exception e) {
            logger.error("Error during module creation. Transaction status: {}", TransactionSynchronizationManager.isActualTransactionActive(), e);
            throw e;
        }
    }

    public List<ModuleRequestDTO> getModulesByUsername(String username){
        List<CustomModule> modules = moduleRepository.findByAuthorUsername(username);
        logger.info("received a request to get modules for user " + username);

        return modules.stream()
                .map(module -> {
                    List<Card> cards = module.getCards();
                    List<CardDTO> cardsDTO = cards.stream()
                            .map(card -> {
                                CardDTO cardDTO = new CardDTO();
                                cardDTO.setTerm(card.getTerm());
                                cardDTO.setDefinition(card.getDefinition());
                                return cardDTO;
                            })
                            .collect(Collectors.toList());

                    ModuleRequestDTO response = new ModuleRequestDTO();
                    response.setModuleName(module.getModuleName());
                    response.setAuthorUsername(username);
                    response.setCards(cardsDTO);
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<ModuleRequestDTO> getALlModules(){
        List<CustomModule> modules = moduleRepository.findAll();
        logger.info("received a request to  get all modules");

        return modules.stream()
                .map(module -> {
                    List<Card> cards = module.getCards();
                    List<CardDTO> cardsDTO = cards.stream()
                            .map(card -> {
                                CardDTO cardDTO = new CardDTO();
                                cardDTO.setTerm(card.getTerm());
                                cardDTO.setDefinition(card.getDefinition());
                                return cardDTO;
                            })
                            .collect(Collectors.toList());
                    ModuleRequestDTO response = new ModuleRequestDTO();
                    response.setModuleName(module.getModuleName());
                    response.setCards(cardsDTO);
                    response.setAuthorUsername(module.getAuthorUsername());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<ModuleRequestDTO> getSearchResult(String request){
        logger.info("received requests to find module with name: "+ request);
        List<CustomModule> modules = moduleRepository.findByRequest(request);
        return modules.stream()
                .map(module -> {
                    List<Card> cards = module.getCards();
                    List<CardDTO> cardsDTO = cards.stream()
                            .map(card -> {
                                CardDTO cardDTO = new CardDTO();
                                cardDTO.setTerm(card.getTerm());
                                cardDTO.setDefinition(card.getDefinition());
                                return cardDTO;
                            })
                            .collect(Collectors.toList());

                    ModuleRequestDTO response = new ModuleRequestDTO();
                    response.setModuleName(module.getModuleName());
                    response.setAuthorUsername(module.getAuthorUsername());
                    response.setCards(cardsDTO);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
