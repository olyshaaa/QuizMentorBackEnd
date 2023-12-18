package com.example.backendquizmentor.repos;

import com.example.backendquizmentor.model.CustomUser;
import com.example.backendquizmentor.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@Service
public class UserdetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserdetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(UserdetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        logger.info("Trying to authenticate user with login: {}", login);
        CustomUser customUser = userService.findByLogin(login);
        if(customUser == null){
            logger.warn("User not found with login: {}", login);
            throw new UsernameNotFoundException(login + "not found");
        }

        List<GrantedAuthority> roles = Arrays.asList(
                new SimpleGrantedAuthority(customUser.getRole().toString())
        );
        logger.info("User {} successfully authenticated", login);
        return new User(customUser.getLogin(), customUser.getPassword(), roles);
    }
}
