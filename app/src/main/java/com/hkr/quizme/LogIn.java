package com.hkr.quizme;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hkr.quizme.database_utils.entities.User;
import com.hkr.quizme.global_data.CurrentUser;
import com.plattysoft.leonids.ParticleSystem;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity implements View.OnClickListener {

    //Facebook text account:

    //freddie_xibtgzr_mercury@tfbnw.net
    //test_konto

    private EditText emailInput, passwordInput;
    private Button logInBtn;
    private TextView signUpBtn;
    private ImageView logoBtn;
    private LoginButton logInWithFB;
    private CallbackManager callbackManager;
    private static int backCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);
        LoginManager.getInstance().logOut();

        //Initialize the views
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        logInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpText);
        logInWithFB = findViewById(R.id.fbSignInBtn);
        logoBtn = findViewById(R.id.logo_image_log_in);

        callbackManager = CallbackManager.Factory.create();
        logInWithFB.setReadPermissions("email", "public_profile");

        logoBtn.setOnClickListener(this);
        logInBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        if (v == logInBtn) {

            v.startAnimation(animation);
            // TODO: If the user press the log in button
            User user = new User(emailInput.getText().toString());
            user.hashAndSetPassword(passwordInput.getText().toString());
            user = user.login(this);
            if (user == null) {
                return;
            }
            CurrentUser.getInstance().setUser(user);
            Intent f = new Intent(this, MainActivity.class);
            f.putExtra("normalLogIN", "yes");
            startActivity(f);
        }
        if (v == signUpBtn) {

            v.startAnimation(animation);
            Intent singUpIntent = new Intent(LogIn.this, SignUp.class);
            startActivity(singUpIntent);
        }

        if (v == logoBtn) {

            //Shake the logo
            Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
            v.startAnimation(animation2);
            playQuizMeSound(this);
        }
    }

    public static void playQuizMeSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.quiz_me);
        mediaPlayer.start();
    }

    public void fb() {
        logInWithFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken == null) {

            } else {
                loadUserProfile(currentAccessToken);
            }
        }
    };


    public void loadUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    User user = new User(object.getString("first_name"), object.getString("last_name"), object.getString("email"));
                    User foundUser = user.findUser(emailInput.getContext());
                    if (foundUser != null) {
                        user = foundUser;
                        CurrentUser.getInstance().setUser(user);
                    } else {
                        user.registerFacebook();
                        CurrentUser.getInstance().setUser(user.findUser(emailInput.getContext()));
                    }
                    String profileImage = "https://graph.facebook.com/" + object.getString("id") + "/picture?type=large&redirect=true&width=600&height=600";
                    user.setImage(profileImage);
                    Intent mainIntent = new Intent(logInBtn.getContext(), MainActivity.class);

                    startActivity(mainIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onBackPressed() {
        backCounter++;
        if (backCounter >= 2) {
            backCounter = 0;
            this.finishAffinity();
        } else {

        }
    }

}
