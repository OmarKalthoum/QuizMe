package com.hkr.quizme.database_utils;

import android.util.Log;

import com.hkr.quizme.database_utils.entities.Quiz;
import com.hkr.quizme.database_utils.entities.QuizRating;
import com.hkr.quizme.global_data.CurrentUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public List<Quiz> getQuizzesInSubject(int subjectId) {
        List<Quiz> result = new ArrayList<>();
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("subjectId", subjectId);
            JSONObject response = communicator.apiCallForResponse("/quizzes-in-subject", "POST", params);
            if (response.getBoolean("success")) {
                JSONArray array = response.getJSONArray("quizzes");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonQuiz = array.getJSONObject(i);
                    Quiz quiz = new Quiz(jsonQuiz.getInt("id"), jsonQuiz.getString("name"));
                    result.add(quiz);
                }
            }
        } catch (JSONException exception) {
            Log.e("QuizDAO::", exception.toString());
        }
        return result;
    }

    public boolean rateQuiz(QuizRating rating) {
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("quizId", rating.getQuizId());
            params.put("userId", rating.getUserId());
            params.put("rating", rating.getRating());
            JSONObject response = communicator.apiCallForResponse("/rate-quiz", "POST", params);
            return response.getBoolean("success");
        } catch (JSONException exception) {
            Log.e("QuizDAO::", exception.toString());
            return false;
        }
    }

    public double getRating(Quiz object) {
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("quizId", object.getId());
            JSONObject response = communicator.apiCallForResponse("/get-rating", "POST", params);
            if (response.getBoolean("success")) {
                return response.getDouble("rating");
            } else {
                return 0.0;
            }
        } catch (JSONException exception) {
            Log.e("QuizDAO::", exception.toString());
            return 0.0f;
        }
    }
}
