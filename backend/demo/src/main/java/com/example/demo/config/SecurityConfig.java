package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // Bean pour encoder les mots de passe, utilisé si tu veux hasher avec BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean pour désactiver toutes les protections de sécurité (dev uniquement)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // Désactiver la protection CSRF (ok pour API REST en dev)
                .authorizeRequests()
                .anyRequest().permitAll()  // Autoriser toutes les requêtes
                .and()
                .httpBasic().disable();  // Désactiver le formulaire/login HTTP Basic

        return http.build();
    }
}
