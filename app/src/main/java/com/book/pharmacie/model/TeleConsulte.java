package com.book.pharmacie.model;

public class TeleConsulte {
    private String id;
    private String NomDocteur;
    private String Typeconsulte;
    private String NomPatient;
    private String DateConsulte;
    private String HeureConsulte;

    public TeleConsulte() {
    }

    public TeleConsulte(String id, String nomDocteur,String Typeconsulte, String nomPatient, String dateConsulte, String heureConsulte) {
        this.id = id;
        NomDocteur = nomDocteur;
        Typeconsulte = Typeconsulte;
        NomPatient = nomPatient;
        DateConsulte = dateConsulte;
        HeureConsulte = heureConsulte;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNomDocteur() {
        return NomDocteur;
    }

    public void setNomDocteur(String nomDocteur) {
        NomDocteur = nomDocteur;
    }


    public String getTypeconsulte() {
        return Typeconsulte;
    }

    public void setTypeconsulte(String typeconsulte) {
        Typeconsulte = typeconsulte;
    }
    public String getNomPatient() {
        return NomPatient;
    }

    public void setNomPatient(String nomPatient) {
        NomPatient = nomPatient;
    }

    public String getDateConsulte() {
        return DateConsulte;
    }

    public void setDateConsulte(String dateConsulte) {
        DateConsulte = dateConsulte;
    }

    public String getHeureConsulte() {
        return HeureConsulte;
    }

    public void setHeureConsulte(String heureConsulte) {
        HeureConsulte = heureConsulte;
    }
}
