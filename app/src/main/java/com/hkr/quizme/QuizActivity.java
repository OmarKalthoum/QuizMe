package com.hkr.quizme;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button1, button2, button3, button4;
    private TextView timer, totalQuestions, question;
    private static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        timer = findViewById(R.id.timer);
        totalQuestions = findViewById(R.id.totalQuestions);
        question = findViewById(R.id.question);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        counter++;
        if (counter > 3) {
            counter = 0;
            Intent intent = new Intent(this, QuizResultActivity.class);
            startActivity(intent);
        } else {
            TransitionDrawable transition = (TransitionDrawable) v.getBackground();
            transition.startTransition(300);
            if (v == button1) {
                setTranstionGrey(button2);
                setTranstionGrey(button3);
                setTranstionGrey(button4);

            }
            if (v == button2) {
                setTranstionGrey(button1);
                setTranstionGrey(button3);
                setTranstionGrey(button4);
            }
            if (v == button3) {
                setTranstionGrey(button2);
                setTranstionGrey(button1);
                setTranstionGrey(button4);
            }
            if (v == button4) {
                setTranstionGrey(button2);
                setTranstionGrey(button1);
                setTranstionGrey(button3);
            }
        }
    }

    public void setTranstionGrey(Button button) {
        button.setBackground(getDrawable(R.drawable.transition_button_grey));
        TransitionDrawable transition2 = (TransitionDrawable) button.getBackground();
        transition2.startTransition(100);
        button.setClickable(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);

    }

    @Override
    public void onBackPressed() {
      warningDialog();
    }

    public void warningDialog(){
        final Dialog warningDialog = new Dialog(this);
        warningDialog.setContentView(R.layout.warning_alert_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Button okBtn = warningDialog.findViewById(R.id.ok_button);
        final Button cancelBtn = warningDialog.findViewById(R.id.cancel_button);
        final TextView title = warningDialog.findViewById(R.id.title_dialog);
        title.setText("Are you sure you want to exit the quiz?");

        warningDialog.setCancelable(false);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:::: exit the quiz without saving any info
                Toast.makeText(getBaseContext(), "Your result will not be saved", Toast.LENGTH_LONG).show();
                warningDialog.dismiss();
            }
        });

        warningDialog.show();
    }
}
