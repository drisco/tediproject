package com.book.pharmacie.model;

public class User {
    private String userId;
    private String name;
    private String email;
    private String phoneNumber;
    private String mdp;

    public User() {
    }

    // Constructeur avec paramètres
    public User(String userId, String name, String email, String phoneNumber, String mdp) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.mdp = mdp; // Ajoutez le mot de passe ici
    }

    // Getters et setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}

