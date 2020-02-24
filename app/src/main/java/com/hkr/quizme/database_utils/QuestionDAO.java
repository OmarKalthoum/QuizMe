package com.hkr.quizme.database_utils;

import android.util.JsonWriter;
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
                AnswerDAO answerDAO = new AnswerDAO();
                current.setAnswers(answerDAO.getIncorrectAnswers(current.getId()));
                current.getAnswers().add(answerDAO.getCorrectAnswer(current.getId()));
                result.add(current);
                Log.d("QUESTION_DAO::", current.getQuestion());
            }
        } catch (JSONException exception) {
            Log.d("QUESTIONS:::", exception.getMessage());
        }
        return result;
    }
}
