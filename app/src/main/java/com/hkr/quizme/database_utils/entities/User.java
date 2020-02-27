package com.hkr.quizme.database_utils.entities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.hkr.quizme.database_utils.UserDAO;
import com.hkr.quizme.database_utils.hashing.PasswordHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String hash;
    private int points;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User(int id, String firstName, String lastName, String email, int points) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.points = points;
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String email) {
        this.email = email;
    }

    public void hashAndSetPassword(String password) {
        PasswordHandler handler = new PasswordHandler();
        this.hash = handler.hash(password);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean register() {
        try {
            return new RegisterUserTask().execute(this).get();
        } catch (InterruptedException | ExecutionException exception) {
            Log.e("User:::", exception.getMessage());
            return false;
        }
    }

    public boolean updatePoints() {
        try {
            return new UpdatePointsTask().execute(this).get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("User::", exception.toString());
            return false;
        }
    }

    public boolean registerFacebook() {
        try {
            return new RegisterFacebookUserTask().execute(this).get();
        } catch (InterruptedException | ExecutionException exception) {
            Log.e("User:::", exception.getMessage());
            return false;
        }
    }

    public boolean checkUniqueEmail() {
        try {
            JSONObject result = new CheckUniqueEmailTask().execute(this).get();
            if (result != null && result.getBoolean("success")) {
                return true;
            }
            return false;
        } catch (InterruptedException | ExecutionException | JSONException exception) {
            Log.e("User:::", exception.getMessage());
            return false;
        }
    }

    public boolean checkUniqueEmail(Context context) {
        try {
            JSONObject result = new CheckUniqueEmailTask().execute(this).get();
            if (result != null) {
                if (result.getBoolean("success") && result.getBoolean("unique")) {
                    return true;
                } else if (result.getBoolean("success")) {
                    Toast.makeText(context, "A user with that email already exists.", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(context, result.getString("message"), Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            return false;
        } catch (InterruptedException | ExecutionException | JSONException exception) {
            Log.e("User:::", exception.toString());
            return false;
        }
    }

    public User login(Context context) {
        try {
            JSONObject result = new LoginUserTask().execute(this).get();
            if (result != null) {
                if (result.getBoolean("success")) {
                    Log.d("User::", result.toString());
                    return new User(result.getInt("id"), result.getString("firstName"), result.getString("lastName"), result.getString("email"), result.getInt("points"));
                } else {
                    Toast.makeText(context, result.getString("message"), Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        } catch (InterruptedException | ExecutionException | JSONException exception) {
            Log.e("User:::", exception.toString());
        }
        return null;
    }

    public User findUser(Context context) {
        try {
            JSONObject result = new FindUserTask().execute(this).get();
            if (result != null) {
                if (result.getBoolean("success")) {
                    if (result.getBoolean("exists")) {
                        JSONObject userJSON = result.getJSONObject("user");
                        return new User(userJSON.getInt("id"), userJSON.getString("firstName"), userJSON.getString("lastName"), userJSON.getString("email"), userJSON.getInt("points"));
                    }
                } else {
                    Toast.makeText(context, result.getString("message"), Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
        } catch (ExecutionException | InterruptedException | JSONException exception) {
            Log.e("User:::", exception.toString());
        }
        return null;
    }

    private static class RegisterUserTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.insert(users[0]);
        }
    }

    private static class RegisterFacebookUserTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.insertFacebookUser(users[0]);
        }
    }

    private static class LoginUserTask extends AsyncTask<User, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.login(users[0]);
        }
    }

    private static class CheckUniqueEmailTask extends AsyncTask<User, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.checkUniqueEmail(users[0]);
        }
    }

    private static class FindUserTask extends AsyncTask<User, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(User... users) {
            UserDAO dao = new UserDAO();
            return dao.findUser(users[0]);
        }
    }

    private static class UpdatePointsTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            return new UserDAO().updateUserPoints(users[0]);
        }
    }
}
