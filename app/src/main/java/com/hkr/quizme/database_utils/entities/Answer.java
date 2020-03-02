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

    public Answer(boolean correct, String answer) {
        this.correct = correct;
        this.answer = answer;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getAnswer() {
        return answer;
    }
}
