package com.book.pharmacie.model;

public class Doctor {
    private String name;
    private String speciality;
    private double rating;
    private String distance;
    private int imageResId;

    public Doctor(String name, String speciality, double rating, String distance, int imageResId) {
        this.name = name;
        this.speciality = speciality;
        this.rating = rating;
        this.distance = distance;
        this.imageResId = imageResId;
    }

    // Getters
    public String getName() { return name; }
    public String getSpeciality() { return speciality; }
    public double getRating() { return rating; }
    public String getDistance() { return distance; }
    public int getImageResId() { return imageResId; }
}

