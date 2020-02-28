package com.hkr.quizme.database_utils;

import android.util.Log;

import com.hkr.quizme.database_utils.entities.Answer;
import com.hkr.quizme.database_utils.entities.Question;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionDAO implements DAO<Question> {
    @Override
    public JSONObject updateInternal(Question object) {
        return null;
    }

    @Override
    public JSONObject updateExternal(Question object) {
        return null;
    }

    @Override
    public boolean insert(Question object) {
        return false;
    }

    public List<Question> getQuestions(int quizId) {
        List<Question> result = new ArrayList<>();
        try {
            APICommunicator communicator = new APICommunicator();
            JSONObject params = new JSONObject();
            params.put("quizId", quizId);
            JSONObject response = communicator.apiCallForResponse("/questions-in-quiz", "POST", params);
            Log.d("QUESTION:::", response.toString());
            JSONArray questions = response.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                JSONObject question = questions.getJSONObject(i);
                Question current = new Question(question.getInt("id"), question.getString("question"));
                result.add(current);
                Log.d("QUESTION_DAO::", current.getQuestion());
            }
        } catch (JSONException exception) {
            Log.d("QUESTIONS:::", exception.getMessage());
        }
        return result;
    }

    public boolean fetchAnswers(Question object) {
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("questionId", object.getId());
            JSONObject response = communicator.apiCallForResponse("/full-question", "POST", params);
            Log.d("QuestionDAO::", response.toString());
            JSONArray incorrectAnswers = response.getJSONArray("incorrectAnswers");
            List<Answer> answers = new ArrayList<>();
            for (int i = 0; i < incorrectAnswers.length(); i++) {
                JSONObject answer = incorrectAnswers.getJSONObject(i);
                answers.add(new Answer(answer.getInt("id"), false, answer.getString("answer")));
            }
            JSONObject correctAnswer = response.getJSONObject("correctAnswer");
            answers.add(new Answer(correctAnswer.getInt("id"), true, correctAnswer.getString("answer")));
            object.setAnswers(answers);
            object.scrambleAnswers();
            return response.getBoolean("success");
        } catch (JSONException exception) {
            Log.e("QuestionDAO::", exception.toString());
        }
        return false;
    }
}
