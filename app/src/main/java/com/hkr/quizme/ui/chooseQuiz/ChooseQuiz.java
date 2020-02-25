package com.hkr.quizme.ui.chooseQuiz;

public class ChooseQuiz {

    private String title, rating;

    public ChooseQuiz(String title, String rating) {
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
