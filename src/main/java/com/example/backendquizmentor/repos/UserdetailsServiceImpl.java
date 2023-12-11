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

import java.util.Arrays;
import java.util.List;

@Service
public class UserdetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserdetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{
        CustomUser customUser = userService.findByLogin(login);
        if(customUser == null)
            throw new UsernameNotFoundException(login + "not found");

        List<GrantedAuthority> roles = Arrays.asList(
                new SimpleGrantedAuthority(customUser.getRole().toString())
        );

        return new User(customUser.getLogin(), customUser.getPassword(), roles);
    }
}
