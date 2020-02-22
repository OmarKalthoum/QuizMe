package com.hkr.quizme.ui.createQuiz;

public class Question {
    private String question, correctAnswerOne, correctAnswerTwo, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour;


    public Question(String question, String correctAnswerOne, String correctAnswerTwo, String wrongAnswerOne, String wrongAnswerTwo, String wrongAnswerThree, String wrongAnswerFour) {
        this.question = question;
        this.correctAnswerOne = correctAnswerOne;
        this.correctAnswerTwo = correctAnswerTwo;
        this.wrongAnswerOne = wrongAnswerOne;
        this.wrongAnswerTwo = wrongAnswerTwo;
        this.wrongAnswerThree = wrongAnswerThree;
        this.wrongAnswerFour = wrongAnswerFour;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswerOne() {
        return correctAnswerOne;
    }

    public void setCorrectAnswerOne(String correctAnswerOne) {
        this.correctAnswerOne = correctAnswerOne;
    }

    public String getCorrectAnswerTwo() {
        return correctAnswerTwo;
    }

    public void setCorrectAnswerTwo(String correctAnswerTwo) {
        this.correctAnswerTwo = correctAnswerTwo;
    }

    public String getWrongAnswerOne() {
        return wrongAnswerOne;
    }

    public void setWrongAnswerOne(String wrongAnswerOne) {
        this.wrongAnswerOne = wrongAnswerOne;
    }

    public String getWrongAnswerTwo() {
        return wrongAnswerTwo;
    }

    public void setWrongAnswerTwo(String wrongAnswerTwo) {
        this.wrongAnswerTwo = wrongAnswerTwo;
    }

    public String getWrongAnswerThree() {
        return wrongAnswerThree;
    }

    public void setWrongAnswerThree(String wrongAnswerThree) {
        this.wrongAnswerThree = wrongAnswerThree;
    }

    public String getWrongAnswerFour() {
        return wrongAnswerFour;
    }

    public void setWrongAnswerFour(String wrongAnswerFour) {
        this.wrongAnswerFour = wrongAnswerFour;
    }
}
