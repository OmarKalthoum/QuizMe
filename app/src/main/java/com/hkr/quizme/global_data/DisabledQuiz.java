package com.hkr.quizme.global_data;

import java.util.Date;

public class DisabledQuiz {
    private int quizId;
    private Date timeout;

    public DisabledQuiz(int quizId, Date timeout) {
        this.quizId = quizId;
        this.timeout = timeout;
    }

    public int getQuizId() {
        return quizId;
    }

    public Date getTimeout() {
        return timeout;
    }
}
