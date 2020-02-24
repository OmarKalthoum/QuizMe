package com.hkr.quizme.database_utils;

import android.util.Log;

import com.hkr.quizme.database_utils.entities.Answer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnswerDAO implements DAO<Answer> {
    @Override
    public JSONObject updateInternal(Answer object) {
        return null;
    }

    @Override
    public JSONObject updateExternal(Answer object) {
        return null;
    }

    @Override
    public boolean insert(Answer object) {
        return false;
    }

    public Answer getCorrectAnswer(int questionId) {
        try {
            APICommunicator communicator = new APICommunicator();
            JSONObject params = new JSONObject();
            params.put("questionId", questionId);
            JSONObject response = communicator.apiCallForResponse("/correct-answer", "POST", params);
            Log.d("ANSWER:::", response.toString());
            return new Answer(response.getInt("id"), true, response.getString("answer"));
        } catch (JSONException exception) {
            Log.e("ANSWER:::", exception.getMessage());
        }
        return null;
    }

    public List<Answer> getIncorrectAnswers(int questionId) {
        List<Answer> answers = new ArrayList<>();
        try {
            APICommunicator communicator = new APICommunicator();
            JSONObject params = new JSONObject();
            params.put("questionId", questionId);
            JSONObject response = communicator.apiCallForResponse("/incorrect-answers", "POST", params);
            Log.d("ANSWER::", response.toString());
            JSONArray incorrectAnswers = response.getJSONArray("incorrectAnswers");
            Log.d("ANSWER::", Integer.toString(incorrectAnswers.length()));
            Log.d("ANSWER::", response.toString());
            for (int i = 0; i < incorrectAnswers.length(); i++) {
                JSONObject object = incorrectAnswers.getJSONObject(i);
                answers.add(new Answer(object.getInt("id"), false, object.getString("answer")));
            }
        } catch (JSONException exception) {
            Log.e("ANSWER:::", exception.getMessage());
        }
        return answers;
    }
}
