package com.hkr.quizme.database_utils.entities;

public class QuizRating {
    private int userId;
    private int quizId;
    private int rating;

    public QuizRating(int userId, int quizId, int rating) {
        this.userId = userId;
        this.quizId = quizId;
        this.rating = rating;
    }

    public int getUserId() {
        return userId;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getRating() {
        return rating;
    }
}
