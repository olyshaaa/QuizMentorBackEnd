package com.example.backendquizmentor.model;

public class UserRequestDTO {
    private String login;
    private String password;
    private String email;
    //TODO
   // private UserRequestDTO of(String login, String email){
        //return new UserRequestDTO(login, email);
    //}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
