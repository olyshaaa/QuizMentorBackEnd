package com.example.backendquizmentor.services;

import com.example.backendquizmentor.model.Card;
import com.example.backendquizmentor.model.CardDTO;
import com.example.backendquizmentor.model.CustomModule;
import com.example.backendquizmentor.repos.CardRepository;
import com.example.backendquizmentor.repos.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    //dont used
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



            // Логируем информацию о транзакции
            logger.info("Transaction status after save: {}", TransactionSynchronizationManager.isActualTransactionActive());
        } catch (Exception e) {
            // Логируем информацию о транзакции в случае исключения
            logger.error("Error during module creation", e);
        }

        // Логируем информацию о транзакции после завершения метода
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
}
