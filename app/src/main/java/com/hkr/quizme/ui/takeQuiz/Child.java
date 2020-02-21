package com.hkr.quizme.ui.takeQuiz;

public class Child {
    private String name;
    private int id;

    public Child(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() { return id; }

    public void setName(String name) {
        this.name = name;
    }

}
