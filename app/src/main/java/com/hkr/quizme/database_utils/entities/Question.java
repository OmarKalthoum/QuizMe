package com.hkr.quizme.database_utils.entities;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private int id;
    private String question;
    private List<Answer> answers;

    public Question(int id, String question) {
        this.id = id;
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
}
