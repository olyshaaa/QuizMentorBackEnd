package com.example.backendquizmentor.services;

import com.example.backendquizmentor.config.AppConfig;
import com.example.backendquizmentor.model.CustomUser;
import com.example.backendquizmentor.repos.UserRepository;
import com.example.backendquizmentor.roles.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomUser> getAllUsers(){
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public CustomUser findByLogin(String login){
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void deleteUsers(List<Long> ids){
        ids.forEach(id -> {
            Optional<CustomUser> user = userRepository.findById(id);
            user.ifPresent(u ->{
                if( !AppConfig.ADMIN.equals(u.getLogin())){
                    userRepository.deleteById(u.getId());
                }
            });
        });
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

    @Transactional
    public void updateUser(String login, String email){
        CustomUser user = userRepository.findByLogin(login);
        if(user ==null)
            return;
        user.setEmail(email);
        userRepository.save(user);
    }
}
