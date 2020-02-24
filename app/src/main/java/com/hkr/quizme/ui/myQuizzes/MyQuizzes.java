package com.hkr.quizme.ui.myQuizzes;

public class MyQuizzes {

    private String title, rating;

    public MyQuizzes(String title, String rating) {
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
