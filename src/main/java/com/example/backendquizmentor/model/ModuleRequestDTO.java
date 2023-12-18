package com.example.backendquizmentor.model;

import java.util.List;

public class ModuleRequestDTO {
    private String moduleName;
    private List<CardDTO> cards;
    private String authorUsername;

    public String getModuleName() {
        return moduleName;
    }

    public ModuleRequestDTO() {
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
