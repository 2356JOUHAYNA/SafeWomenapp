package com.example.safewo.model;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;

    // Nouveau champ token
    private String token;

    // Getters and setters existants...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter pour le token

    /**
     * Renvoie le jeton d'authentification
     */
    public String getToken() {
        return token;
    }

    /**
     * Définit le jeton d'authentification
     */
    public void setToken(String token) {
        this.token = token;
    }
}