package com.hkr.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hkr.quizme.database_utils.entities.User;

import static com.hkr.quizme.LogIn.playQuizMeSound;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    private EditText userName, email, password,confirmPassword;
    private Button signUoBtn;
    private ImageView userNameIcon, emailIcon, passwordIcon, confirmPasswordIcon, addProfilePic, logoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userName = findViewById(R.id.userNameInput);
        email = findViewById(R.id.emailInput);
        password = findViewById(R.id.passwordInput);
        confirmPassword = findViewById(R.id.confirmPasswordInput);

        signUoBtn = findViewById(R.id.singUpBtn);
        addProfilePic = findViewById(R.id.addProfileImage);
        logoBtn = findViewById(R.id.logo_image_sign_up);

        userNameIcon = findViewById(R.id.userNameIcon);
        emailIcon = findViewById(R.id.emailIcon);
        passwordIcon = findViewById(R.id.passwordIcon);
        confirmPasswordIcon = findViewById(R.id.confirmPasswordIcon);

        signUoBtn.setOnClickListener(this);
        logoBtn.setOnClickListener(this);
        addProfilePic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        if(v == signUoBtn){
            v.startAnimation(animation);
            User user = new User(userName.getText().toString(), email.getText().toString());
            user.hashAndSetPassword(password.getText().toString());
            if (user.checkUniqueDisplayName(this) && user.checkUniqueEmail(this)  && user.register()) {
                Toast.makeText(this, "Registration was successful!", Toast.LENGTH_LONG).show();
                Intent logInIntent = new Intent(this, LogIn.class);
                startActivity(logInIntent);
            }
        }
        if(v == addProfilePic){
            // TODO: Open the gallery and add a pic

        }
        if (v == logoBtn ) {
            //Shake the logo
            Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
            v.startAnimation(animation2);
            playQuizMeSound(this);
        }
    }
}
