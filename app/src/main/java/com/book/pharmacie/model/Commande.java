package com.book.pharmacie.model;

import java.util.List;
import java.util.Map;

public class Commande {

    private String orderId;
    private String userId;
    private List<ProduitQuantite> produits;
    private String totalPrice;
    private String orderStatus;
    private String orderDate;
    private String latitude;
    private String longitude;

    public Commande() {
    }

    public Commande(String orderId, String userId, List<ProduitQuantite> produits, String totalPrice, String orderStatus, String orderDate, String latitude, String longitude) {
        this.orderId = orderId;
        this.userId = userId;
        this.produits = produits;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ProduitQuantite> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitQuantite> produits) {
        this.produits = produits;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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

