package com.hkr.quizme.database_utils;

import android.util.Log;

import com.hkr.quizme.database_utils.entities.Answer;
import com.hkr.quizme.database_utils.entities.Question;
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
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("name", object.getName());
            JSONArray questions = new JSONArray();
            for (Question q : object.getQuestions()) {
                JSONObject question = new JSONObject();
                question.put("question", q.getQuestion());
                JSONArray incorrectAnswers = new JSONArray();
                JSONArray correctAnswers = new JSONArray();
                for (Answer a : q.getAnswers()) {
                    JSONObject answer = new JSONObject();
                    answer.put("answer", a.getAnswer());
                    if (a.isCorrect()) {
                        correctAnswers.put(answer);
                    } else {
                        incorrectAnswers.put(answer);
                    }
                }
                question.put("incorrectAnswers", incorrectAnswers);
                question.put("correctAnswers", correctAnswers);
                questions.put(question);
            }
            params.put("questions", questions);
            JSONObject response = communicator.apiCallForResponse("/create-quiz", "POST", params);

        } catch (JSONException exception) {
            Log.e("QuizDAO::", exception.toString());
        }
        return true;
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
