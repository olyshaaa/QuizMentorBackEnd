package com.example.backendquizmentor.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);


    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/register", "/newuser", "/createModule").permitAll().anyRequest().authenticated()

                )
                .formLogin(formLogin -> formLogin
                       .loginPage("http://localhost:5173/login").
                        loginProcessingUrl("/j_spring_security_check")

                        //.successHandler((request, response, authentication) -> {
                          /// response.setStatus(HttpServletResponse.SC_OK);
                        //})
                        //.failureUrl("http://localhost:5173/login?error")
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                           logger.error("Authentication failed for user: {}", request.getParameter("username"), exception);
                        })
                        .defaultSuccessUrl("http://localhost:5173/home")
                        .usernameParameter("login").passwordParameter("password")
                        .permitAll()

                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/login?denied"));



        //.logout(logout -> logout.logoutUrl("/login").permitAll());
        return http.build();
    }
}
