package com.hkr.quizme.database_utils;

import org.json.JSONException;
import org.json.JSONObject;

public class DAOUtils {
    static JSONObject generateConnectionError() {
        try {
            JSONObject error = new JSONObject();
            error.put("success", false);
            error.put("message", "Could not connect to server.");
            return error;
        } catch (JSONException exception) {
            return null;
        }
    }
}
