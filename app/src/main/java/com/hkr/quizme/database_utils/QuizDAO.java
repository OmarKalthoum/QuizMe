package com.hkr.quizme.database_utils;

import com.hkr.quizme.database_utils.entities.Quiz;

import org.json.JSONObject;

public class QuizDAO implements DAO<Quiz> {
    @Override
    public JSONObject updateInternal(Quiz object) {
        return null;
    }

    @Override
    public JSONObject updateExternal(Quiz object) {
        return null;
    }

    @Override
    public boolean insert(Quiz object) {
        return false;
    }
}
