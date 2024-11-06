package com.book.pharmacie.model;

public class Notification {
    private String id;
    private String type;
    private String message;
    private String date;
    private String heure;

    public Notification() {
    }

    public Notification(String id, String type, String message, String date, String heure) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.date = date;
        this.heure = heure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }
}
