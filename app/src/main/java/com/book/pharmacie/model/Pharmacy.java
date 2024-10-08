package com.book.pharmacie.model;

public class Pharmacy {
    private String name;
    private int imageResId; // Resource ID for the pharmacy image

    public Pharmacy(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}

