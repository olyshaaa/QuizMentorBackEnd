package com.example.backendquizmentor.roles;

public enum UserRole {
    ADMIN, USER;

    @Override
    public String toString(){
        return "ROLE_" + name();
    }
}
