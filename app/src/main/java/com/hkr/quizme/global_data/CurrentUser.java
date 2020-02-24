package com.hkr.quizme.global_data;

import com.hkr.quizme.database_utils.entities.User;

public class CurrentUser {
    private static CurrentUser instance;
    private User user;

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    private CurrentUser() {

    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
