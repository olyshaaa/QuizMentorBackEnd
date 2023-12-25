package com.example.backendquizmentor.config;

import com.example.backendquizmentor.model.CustomUser;
import com.example.backendquizmentor.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthHandler.class);

    private final UserService userService;

    public AuthHandler(UserService userService) {
        this.userService = userService;
    }
    //TODO
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        OAuth2User oauthuser = token.getPrincipal();
        Map<String, Object> attributes = oauthuser.getAttributes();
        String username = (String) attributes.get("name");
        CustomUser existingUser = userService.findByLogin(username);
        logger.info("recieve a request to authentication a user with username: "+username);
        if (existingUser == null){
            CustomUser user = new CustomUser();
            user.setLogin(username);
            String email = (String) attributes.get("email");
            user.setEmail(email);
            //userService.addUser(user);
        }

    }
}
