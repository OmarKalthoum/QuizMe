package com.hkr.quizme.database_utils.entities;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.QuizDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Quiz {
    private int id;
    private String name;
    private List<Question> questions;
    private int subjectId;

    public Quiz(int id) {
        this.id = id;
        questions = new ArrayList<>();
    }

    public Quiz(String name) {
        this.name = name;
        questions = new ArrayList<>();
    }

    public Quiz(int id, String name) {
        this.id = id;
        this.name = name;
        questions = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public static List<Quiz> getQuizzesInSubject(int subjectId) {
        try {
            return new QuizzesInSubject().execute(subjectId).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("Quiz::", exception.toString());
        }
        return new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public boolean rate(int userId, int rating) {
        QuizRating quizRating = new QuizRating(userId, id, rating);
        try {
            return new RateQuizTask().execute(quizRating).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("Quiz::", exception.toString());
            return false;
        }
    }

    public double getRating() {
        try {
            return new GetRatingTask().execute(this).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("Quiz::", exception.toString());
            return 0.0;
        }
    }

    public boolean insert() {
        try {
            return new InsertTask().execute(this).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("Quiz::", exception.toString());
            return false;
        }
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    private static class QuizzesInSubject extends AsyncTask<Integer, Void, List<Quiz>> {
        @Override
        protected List<Quiz> doInBackground(Integer... integers) {
            QuizDAO dao = new QuizDAO();
            return dao.getQuizzesInSubject(integers[0]);
        }
    }

    private static class RateQuizTask extends AsyncTask<QuizRating, Void, Boolean> {
        @Override
        protected Boolean doInBackground(QuizRating... quizRatings) {
            return new QuizDAO().rateQuiz(quizRatings[0]);
        }
    }

    private static class GetRatingTask extends AsyncTask<Quiz, Void, Double> {
        @Override
        protected Double doInBackground(Quiz... quizzes) {
            return new QuizDAO().getRating(quizzes[0]);
        }
    }

    private static class InsertTask extends AsyncTask<Quiz, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Quiz... quizzes) {
            QuizDAO dao = new QuizDAO();
            return dao.insert(quizzes[0]);
        }
    }
}
