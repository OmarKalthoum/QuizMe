package com.hkr.quizme.database_utils.entities;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.QuestionDAO;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Question {
    private int id;
    private String question;
    private List<Answer> answers;

    public Question(int id, String question) {
        this.id = id;
        this.question = question;
        this.answers = new ArrayList<>();
    }

    public Question(String question) {
        this.question = question;
        this.answers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean fetchAnswers() {
        try {
            return new FetchAnswersTask().execute(this).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("Question::", exception.toString());
        }
        return false;
    }

    public void scrambleAnswers() {
        int randomIndex = new SecureRandom().nextInt(answers.size() - 1);
        Answer a = answers.get(randomIndex);
        Answer b = answers.get(answers.size() - 1);
        answers.set(randomIndex, b);
        answers.set(answers.size()-1, a);
    }

    private static class FetchAnswersTask extends AsyncTask<Question, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Question... questions) {
            return new QuestionDAO().fetchAnswers(questions[0]);
        }
    }
}
