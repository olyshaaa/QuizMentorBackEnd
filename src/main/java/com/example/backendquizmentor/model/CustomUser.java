package com.example.backendquizmentor.model;

import com.example.backendquizmentor.roles.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor
public class CustomUser {
    @Id
    @GeneratedValue()
    private long id;

    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String email;

    public CustomUser(String login, String password, UserRole role,
                      String email){
        this.login = login;
        this.password = password;
        this.role = role;
        this.email = email;
    }
}
