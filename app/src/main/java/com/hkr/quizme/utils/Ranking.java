package com.hkr.quizme.utils;

public class Ranking {
    private int points;
    private String name;

    public Ranking(int points, String name) {
        this.points = points;
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }
}
