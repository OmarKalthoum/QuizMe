package com.hkr.quizme.ui.chooseQuiz;

public class ChooseQuiz {
    private int id;
    private String title;
    private double rating;

    public ChooseQuiz(String title, double rating) {
        this.title = title;
        this.rating = rating;
    }

    public ChooseQuiz(int id, String title, double rating) {
        this.id = id;
        this.title = title;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRating() {
        return rating;
    }
}
