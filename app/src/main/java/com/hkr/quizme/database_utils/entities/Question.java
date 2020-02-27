package com.hkr.quizme.database_utils.entities;

import java.security.SecureRandom;
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

    public void scrambleAnswers() {
        int randomIndex = new SecureRandom().nextInt(answers.size() - 1);
        Answer a = answers.get(randomIndex);
        Answer b = answers.get(answers.size() - 1);
        answers.set(randomIndex, b);
        answers.set(answers.size()-1, a);
    }
}
