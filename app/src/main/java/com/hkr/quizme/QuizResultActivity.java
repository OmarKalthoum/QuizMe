package com.hkr.quizme;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class QuizResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Button menuBtn;
    private Context context;
    private TextView reportBtn, resultComment, levelTxt, resultTxt;
    private ProgressBar resultProgBar, levelProgBar;
    private byte rating = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        context = this;
        menuBtn = findViewById(R.id.menuBtn);
        reportBtn = findViewById(R.id.reportBtn);
        resultComment = findViewById(R.id.resultComment);
        levelTxt = findViewById(R.id.levelTextView);
        resultTxt = findViewById(R.id.resultTextView);
        resultProgBar = findViewById(R.id.resultProgBar);
        levelProgBar = findViewById(R.id.levelProgBar);

        menuBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == menuBtn) {

            showRatingDialog();
        }
        if (v == reportBtn) {
            showFeedbackDialog();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showFeedbackDialog() {
        final Dialog feedBackDialog = new Dialog(this);
        feedBackDialog.setContentView(R.layout.report_feedback_alert_dialog_layout);
        feedBackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final TextView feedBackName = feedBackDialog.findViewById(R.id.feedback_name);
        final TextView feedbackEmail = feedBackDialog.findViewById(R.id.feedback_email);
        final TextView feedbackContent = feedBackDialog.findViewById(R.id.feedback_content);
        final Button submitBtn = feedBackDialog.findViewById(R.id.submitReportBtn);
        final Button cancelBtn = feedBackDialog.findViewById(R.id.cancelReportBtn);

        feedBackDialog.setCancelable(false);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedBackDialog.dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = feedBackName.getText().toString();
                String email = feedbackEmail.getText().toString();
                String content = feedbackContent.getText().toString();

                //TODO: Take the information and display it to the admin
                Toast.makeText(context, "Thank you for giving your feedback!", Toast.LENGTH_LONG).show();
                feedBackDialog.dismiss();
            }
        });

        feedBackDialog.show();

    }

    private void showRatingDialog() {
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        Dialog builder = new Dialog(this);
        builder.setContentView(R.layout.rating_feedback_alert_dialog_layout);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final ImageButton one_rate_icon = builder.findViewById(R.id.one_rate_icon);
        final ImageButton two_rate_icon = builder.findViewById(R.id.two_rate_icon);
        final ImageButton three_rate_icon = builder.findViewById(R.id.three_rate_icon);
        final ImageButton four_rate_icon = builder.findViewById(R.id.four_rate_icon);
        final ImageButton five_rate_icon = builder.findViewById(R.id.five_rate_icon);
        final Button caneclBtn = builder.findViewById(R.id.cancelBtn);
        builder.setCancelable(false);


        caneclBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                caneclBtn.startAnimation(animation);
                moveToMain();
            }
        });

        one_rate_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                one_rate_icon.startAnimation(animation);
                rating = 1;
                saveRatingToDB();
                moveToMain();
            }
        });
        two_rate_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                two_rate_icon.startAnimation(animation);
                rating = 2;
                saveRatingToDB();
                moveToMain();
            }
        });
        three_rate_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                three_rate_icon.startAnimation(animation);
                rating = 3;
                saveRatingToDB();
                moveToMain();
            }
        });
        four_rate_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                four_rate_icon.startAnimation(animation);
                rating = 4;
                saveRatingToDB();
                moveToMain();
            }
        });
        five_rate_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                five_rate_icon.startAnimation(animation);
                rating = 5;
                saveRatingToDB();
                moveToMain();
            }
        });

        builder.show();

    }

    public void moveToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(context, "Thank you for giving your feedback!", Toast.LENGTH_LONG).show();

    }

    public void saveRatingToDB() {
        //TODO: save the rating to database if it is not 0 before returning to the main menu
    }
}
