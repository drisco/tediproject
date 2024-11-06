package com.book.pharmacie.Admin.Adapters;

import com.book.pharmacie.model.Commande;

public class OrderWithUser {
    private final Commande commande;
    private final String userName;

    public OrderWithUser(Commande commande, String userName) {
        this.commande = commande;
        this.userName = userName;
    }

    public Commande getCommande() {
        return commande;
    }

    public String getUserName() {
        return userName;
    }
}
