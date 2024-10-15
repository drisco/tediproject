package com.book.pharmacie.model;

public class ProduitQuantite {
    private Product produit;
    private int quantite;

    public ProduitQuantite() {
        // Firebase exige un constructeur sans arguments
    }

    public ProduitQuantite(Product produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    // Getters et setters
    public Product getProduit() {
        return produit;
    }

    public void setProduit(Product produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}

