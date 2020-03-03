package com.hkr.quizme.global_data;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.QuestionDAO;
import com.hkr.quizme.database_utils.entities.Quiz;

import java.util.concurrent.ExecutionException;

public class QuizHolder {
    private final static QuizHolder instance = new QuizHolder();
    private Quiz quiz;
    private int currentQuestion;
    private int points;

    public static QuizHolder getInstance() {
        return instance;
    }

    private QuizHolder() {
        currentQuestion = 0;
    }

    public void initialize(int quizId) {
        try {
            quiz = new InitializeQuizTask().execute(quizId).get();
            currentQuestion = 0;
            points = 0;
        } catch (ExecutionException | InterruptedException exception) {
            Log.d("QuizHolder::", exception.getMessage());
            quiz = null;
        }
    }

    public void reset() {
        quiz = null;
        currentQuestion = 0;
        points = 0;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public int getPoints() {
        return points;
    }

    public boolean registerUserAnswer(int id) {
        if (quiz.getQuestions().get(currentQuestion).getAnswers().get(id).isCorrect()) {
            points++;
            return true;
        }
        Log.d("POIKMS::", Integer.toString(points));
        return false;
    }

    public int getMaxPoints() {
        return quiz.getQuestions().size();
    }

    public int getResultPercentage() {
        return (int)(((double)points / getMaxPoints()) * 100);
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
