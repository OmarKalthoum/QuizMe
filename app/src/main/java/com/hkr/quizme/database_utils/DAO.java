package com.hkr.quizme.database_utils;

import org.json.JSONObject;

public interface DAO <T> {
    JSONObject updateInternal(T object);
    JSONObject updateExternal(T object);
    boolean insert(T object);
}
