package com.book.pharmacie.model;

public class NewsItem {
    private String title;
    private String description;
    private String imageUrl;
    private String publicationDate;
    private String source;
    private String articleUrl;

    // Constructeur
    public NewsItem(String title, String description, String imageUrl, String publicationDate, String source, String articleUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.publicationDate = publicationDate;
        this.source = source;
        this.articleUrl = articleUrl;
    }

    // Getters et Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
}

