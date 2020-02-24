package com.hkr.quizme.ui.previousResult;

public class Result {
    private String title, date, result;

    public Result(String title, String date, String result) {
        this.title = title;
        this.date = date;
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
