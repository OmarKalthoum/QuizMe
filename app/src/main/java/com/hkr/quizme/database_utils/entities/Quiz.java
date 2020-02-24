package com.hkr.quizme.database_utils.entities;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private int id;
    private List<Question> questions;

    public Quiz(int id) {
        this.id = id;
        questions = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
