package com.example.backendquizmentor.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
public class CustomModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "module_name")
    private String moduleName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customModule")
    //@JoinColumn(name= "module_id")
    private List<Card> cards;

    @Column(name = "author_username")
    private String authorUsername;

    public CustomModule(){
        this.cards = new ArrayList<>();
    }
    public CustomModule(String moduleName, String authorUsername) {
        this.moduleName = moduleName;
        this.authorUsername = authorUsername;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card){
        if (cards == null){
            cards = new ArrayList<>();
        }
        cards.add(card);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
