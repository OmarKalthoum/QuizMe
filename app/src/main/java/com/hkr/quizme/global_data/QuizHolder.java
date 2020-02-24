package com.hkr.quizme.global_data;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.QuestionDAO;
import com.hkr.quizme.database_utils.entities.Quiz;

import java.util.concurrent.ExecutionException;

public class QuizHolder {
    private static QuizHolder instance;
    private Quiz quiz;
    private int currentQuestion;

    public static QuizHolder getInstance() {
        if (instance == null) {
            instance = new QuizHolder();
        }
        return instance;
    }

    private QuizHolder() {
        currentQuestion = 0;
    }

    public void initialize(int quizId) {
        try {
            quiz = new InitializeQuizTask().execute(quizId).get();
            currentQuestion = 0;
        } catch (ExecutionException | InterruptedException exception) {
            Log.d("QuizHolder::", exception.getMessage());
            quiz = null;
        }
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void incrementCurrentQuestion() {
        currentQuestion++;
    }

    private static class InitializeQuizTask extends AsyncTask<Integer, Void, Quiz> {
        @Override
        protected Quiz doInBackground(Integer... integers) {
            int quizId = integers[0];
            Quiz quiz = new Quiz(quizId);
            QuestionDAO questionDAO = new QuestionDAO();
            quiz.setQuestions(questionDAO.getQuestions(quizId));
            return quiz;
        }
    }
}
