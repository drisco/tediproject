package com.book.pharmacie.model;

public class QuestionResponse {
    private String question;
    private String response;

    public QuestionResponse() {
        // Constructeur vide nécessaire pour Firebase
    }

    public QuestionResponse(String question, String response) {
        this.question = question;
        this.response = response;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

