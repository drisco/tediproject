package com.book.pharmacie.model;

public class Livreur {
    private String id;
    private String nom;
    private String numero;
    private String email;
    private String mdp;
    private String latitude;
    private String longitude;

    public Livreur() {
        this.id = "Livreur01"; // ID par défaut
        this.nom = "Tedji gael"; // Nom par défaut
        this.numero = "+22569594256"; // Nom par défaut
        this.email = "livreur@gmail.com"; // Nom par défaut
        this.mdp = "livreur123"; // Nom par défaut
        this.latitude = "5.3188"; // Latitude par défaut
        this.longitude = "-4.0154"; // Longitude par défaut
    }

    public Livreur(String id, String nom, String numero, String email, String mdp, String latitude, String longitude) {
        this.id = id;
        this.nom = nom;
        this.numero = numero;
        this.email = email;
        this.mdp = mdp;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
