package com.example.backendquizmentor.services;

import com.example.backendquizmentor.config.AppConfig;
import com.example.backendquizmentor.model.CustomUser;
import com.example.backendquizmentor.model.UserRequestDTO;
import com.example.backendquizmentor.repos.UserRepository;
import com.example.backendquizmentor.roles.UserRole;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    public boolean isUsernameTaken(String username){
        return userRepository.existsByLogin(username);
    }
    @Transactional
    public boolean addUser(String login, String passHash,
                           UserRole role, String email){
        if (userRepository.existsByLogin(login))
            return false;
        CustomUser user = new CustomUser(login, passHash, role, email);
        userRepository.save(user);

        return true;
    }

}
