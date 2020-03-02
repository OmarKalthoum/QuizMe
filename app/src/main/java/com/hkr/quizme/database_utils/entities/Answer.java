package com.hkr.quizme.database_utils.entities;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.AnswerDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Answer {
    private int id;
    private boolean correct;
    private String answer;

    public Answer(int id, boolean correct, String answer) {
        this.id = id;
        this.correct = correct;
        this.answer = answer;
    }


    public boolean isCorrect() {
        return correct;
    }

    public String getAnswer() {
        return answer;
    }

    public static List<Answer> getIncorrectAnswers(int questionId) {
        try {
            return new GetIncorrectAnswersTask().execute(questionId).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.d("ANSWER::", exception.toString());
        }
        return new ArrayList<>();
    }

    public static Answer getCorrectAnswer(int questionId) {
        try {
            return new GetCorrectAnswerTask().execute(questionId).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.d("ANSWER::", exception.toString());
        }
        return null;
    }

    private static class GetIncorrectAnswersTask extends AsyncTask<Integer, Void, List<Answer>> {
        @Override
        protected List<Answer> doInBackground(Integer... integers) {
            AnswerDAO dao = new AnswerDAO();
            return dao.getIncorrectAnswers(integers[0]);
        }
    }

    private static class GetCorrectAnswerTask extends AsyncTask<Integer, Void, Answer> {
        @Override
        protected Answer doInBackground(Integer... integers) {
            AnswerDAO dao = new AnswerDAO();
            return dao.getCorrectAnswer(integers[0]);
        }
    }
}
