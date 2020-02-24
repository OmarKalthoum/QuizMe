package com.hkr.quizme.database_utils;

import android.util.Log;

import com.hkr.quizme.database_utils.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDAO implements DAO<User> {
    @Override
    public JSONObject updateInternal(User object) {
        return null;
    }

    @Override
    public JSONObject updateExternal(User object) {
        return null;
    }

    @Override
    public boolean insert(User object) {
        APICommunicator communicator = new APICommunicator();
        JSONObject params = new JSONObject();
        JSONObject response = null;
        try {
            params.put("email", object.getEmail());
            params.put("displayName", object.getDisplayName());
            params.put("hash", object.getHash());
            response = communicator.apiCallForResponse("/register", "POST", params);
            boolean success = response.getBoolean("success");
            if (!success) {
                Log.d("UserDAO::", response.getString("message"));
            }
            return success;
        } catch (JSONException exception) {
            Log.e("UserDAO::", exception.getMessage());
        }
        return false;
    }

    public JSONObject checkUniqueEmail(User object) {
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("email", object.getEmail());
            JSONObject response = communicator.apiCallForResponse("/email-unique", "POST", params);
            if (response.getBoolean("success")) {
                return response;
            }
        } catch (JSONException exception) {
            Log.e("JSON::", exception.getMessage());
        }
        return DAOUtils.generateConnectionError();
    }

    public JSONObject checkUniqueDisplayName(User object) {
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("displayName", object.getDisplayName());
            JSONObject response = communicator.apiCallForResponse("/display-name-unique", "POST", params);
            if (response.getBoolean("success")) {
                return response;
            }
        } catch (JSONException exception) {
            Log.e("JSON::", exception.getMessage());
        }
        return DAOUtils.generateConnectionError();
    }

    public JSONObject login(User object) {
        APICommunicator communicator = new APICommunicator();
        try {
            JSONObject params = new JSONObject();
            params.put("displayName", object.getDisplayName());
            params.put("hash", object.getHash());
            return communicator.apiCallForResponse("/login", "POST", params);
        } catch (JSONException exception) {
            Log.e("JSON::", exception.getMessage());
        }
        return DAOUtils.generateConnectionError();
    }
}
