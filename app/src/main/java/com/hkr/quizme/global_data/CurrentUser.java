package com.hkr.quizme.global_data;

import com.hkr.quizme.database_utils.entities.User;

public class CurrentUser {
    private final static CurrentUser instance = new CurrentUser();
    private User user;

    public static CurrentUser getInstance() {
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
