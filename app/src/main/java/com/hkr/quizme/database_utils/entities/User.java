package com.hkr.quizme.database_utils.entities;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.UserDAO;
import com.hkr.quizme.database_utils.hashing.PasswordHandler;

import java.util.concurrent.ExecutionException;

public class User {
    private int id;
    private String displayName;
    private String email;
    private String hash;

    public User(int id, String displayName, String email) {
        this.id = id;
        this.displayName = displayName;
        this.email = email;
        this.hash = hash;
    }

    public User(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public void hashAndSetPassword(String password) {
        PasswordHandler handler = new PasswordHandler();
        this.hash = handler.hash(password);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() {
        return hash;
    }

    public boolean register() {
        try {
            return new RegisterUserTask().execute(this).get();
        } catch (InterruptedException | ExecutionException exception) {
            Log.e("User:::", exception.getMessage());
            return false;
        }
    }

    public boolean checkUniqueEmail() {
        try {
            return new CheckUniqueEmailTask().execute(this).get();
        } catch (InterruptedException | ExecutionException exception) {
            Log.e("User:::", exception.getMessage());
            return false;
        }
    }

    private class RegisterUserTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.insert(users[0]);
        }
    }

    private class CheckUniqueEmailTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.checkUniqueEmail(users[0]);
        }
    }
}
