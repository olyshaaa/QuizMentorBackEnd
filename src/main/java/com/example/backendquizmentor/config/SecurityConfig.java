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
                        .requestMatchers("/", "/login", "/signup", "/register", "/createModule", "/modules/**", "/logout", "/newuser", "/search/**", "/j_spring_security_check").permitAll().anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                       .loginPage("https://quiz-mentor.vercel.app/login").
                        loginProcessingUrl("/j_spring_security_check")

                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                           logger.error("Authentication failed for user: {}", request.getParameter("username"), exception);
                        })
                        .usernameParameter("login").passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login").permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/login?denied"));

        return http.build();
    }
}
