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

import androidx.appcompat.app.AppCompatActivity;

public class Log_In extends AppCompatActivity implements View.OnClickListener {

    private EditText userNameInput, passwordInput;
    private Button logInBtn;
    private TextView signUpBtn;
    private ImageView logInWithFBBtn, logoBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        //Initialize the views
        userNameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        logInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpText);
        logInWithFBBtn = findViewById(R.id.fbSignInBtn);
        logoBtn = findViewById(R.id.logo_image_log_in);

        logoBtn.setOnClickListener(this);
        logInBtn.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        logInWithFBBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        if (v == logInBtn) {
            v.startAnimation(animation);
            // TODO: If the user press the log in button

            //test
            Intent f = new Intent(this, MainActivity.class);
            startActivity(f);
        }
        if (v == signUpBtn) {
            // TODO: Move to sign_up intent
            v.startAnimation(animation);
            Intent singUpIntent = new Intent(Log_In.this, SignUp.class);
            startActivity(singUpIntent);
        }
        if (v == logInWithFBBtn) {
            v.startAnimation(animation);
            // TODO: If the user choose to log in with FB
        }
        if (v == logoBtn) {
            //Shake the logo
            Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
            v.startAnimation(animation2);
            playQuizMeSound(this);
        }
    }

    public static void playQuizMeSound(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.quiz_me);
        mediaPlayer.start();
    }
}
