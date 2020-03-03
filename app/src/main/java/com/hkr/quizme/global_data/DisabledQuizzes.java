package com.hkr.quizme.global_data;

import android.annotation.SuppressLint;
import android.security.keystore.UserNotAuthenticatedException;

import com.hkr.quizme.database_utils.entities.Quiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DisabledQuizzes {
    private static DisabledQuizzes instance = new DisabledQuizzes();
    private static List<DisabledQuiz> disabledQuizzes;

    public static DisabledQuizzes getInstance() {
        return instance;
    }

    private DisabledQuizzes() {
        disabledQuizzes = new ArrayList<>();
    }

    public void addQuiz(Quiz quiz, Date timeout) {
        disabledQuizzes.add(new DisabledQuiz(quiz.getId(), timeout));
    }

    public void removeQuiz(Quiz quiz) {
        for (DisabledQuiz disabledQuiz : disabledQuizzes) {
            if (disabledQuiz.getQuizId() == quiz.getId()) {
                disabledQuizzes.remove(disabledQuiz);
                break;
            }
        }
    }

    public boolean contains(Quiz quiz) {
        for (DisabledQuiz disabledQuiz : disabledQuizzes) {
            if (disabledQuiz.getQuizId() == quiz.getId()) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("DefaultLocale")
    public String getTimeLeft(Quiz quiz) {
        for (DisabledQuiz disabledQuiz : disabledQuizzes) {
            if (disabledQuiz.getQuizId() == quiz.getId()) {
                Date now = new Date();
                long minutes = (disabledQuiz.getTimeout().getTime() - now.getTime()) / 1000 / 60;
                long seconds = ((disabledQuiz.getTimeout().getTime() - now.getTime()) / 1000) - minutes * 60;
                return String.format("%02d:%02d", minutes, seconds);
            }
        }
        return "0...";
    }
}
