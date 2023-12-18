package com.example.backendquizmentor.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "term")
    private String term;
    @Column(name = "definition")
    private String definition;

    @ManyToOne(targetEntity = CustomModule.class)
    @JoinColumn(name = "custom_module_id")
    private CustomModule customModule;

    public Card() {
    }

    public Card(String term, String definition, CustomModule customModule) {
        this.term = term;
        this.definition = definition;
        this.customModule = customModule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public CustomModule getCustomModule() {
        return customModule;
    }

    public void setCustomModule(CustomModule customModule) {
        this.customModule = customModule;
    }
}
