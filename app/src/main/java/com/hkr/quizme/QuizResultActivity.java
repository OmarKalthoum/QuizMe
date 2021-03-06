package com.hkr.quizme;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.hkr.quizme.database_utils.entities.Quiz;
import com.hkr.quizme.global_data.CurrentUser;
import com.hkr.quizme.global_data.DisabledQuizzes;
import com.hkr.quizme.global_data.QuizHolder;
import com.hkr.quizme.ui.previousResult.Result;
import com.hkr.quizme.utils.Rankings;
import com.plattysoft.leonids.ParticleSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;

import static com.hkr.quizme.QuizActivity.totalQuestionNumbers;
import static com.hkr.quizme.QuizActivity.totalRightAnswers;
import static com.hkr.quizme.StartActivity.counterbby;
import static com.hkr.quizme.ui.chooseQuiz.ChooseQuizAdapter.quizTitle;

public class QuizResultActivity extends AppCompatActivity implements View.OnClickListener {

    private Button menuBtn;
    private Context context;
    private TextView reportBtn, resultComment, levelTxt, resultTxt, levelInDigits;
    private ProgressBar resultProgBar, levelProgBar;
    private byte rating = 0;
    private int counterProgressResult = 0;
    private Handler handler = new Handler();


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);


        context = this;
        menuBtn = findViewById(R.id.menuBtn);
        levelInDigits = findViewById(R.id.levelInDigitsResult);
        reportBtn = findViewById(R.id.reportBtn);
        resultComment = findViewById(R.id.resultComment);
        levelTxt = findViewById(R.id.levelTextView);
        resultTxt = findViewById(R.id.resultTextView);
        resultProgBar = findViewById(R.id.resultProgBar);
        levelProgBar = findViewById(R.id.levelProgBar);


        if (QuizHolder.getInstance().getResultPercentage() != 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (counterProgressResult != QuizHolder.getInstance().getResultPercentage()) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                resultProgBar.setProgress(counterProgressResult);
                                resultTxt.setText(counterProgressResult + "%");
                                if (counterProgressResult < 50) {
                                    resultComment.setText("KEEP TRYING " + CurrentUser.getInstance().getUser().getFirstName().toUpperCase());
                                } else if (counterProgressResult < 70) {
                                    resultComment.setText("GOOD JOB " + CurrentUser.getInstance().getUser().getFirstName().toUpperCase());
                                } else if (counterProgressResult < 85) {
                                    resultComment.setText("GREAT " + CurrentUser.getInstance().getUser().getFirstName().toUpperCase());
                                    animAllAnswersCorrect();
                                } else {
                                    resultComment.setText("EXCELLENT " + CurrentUser.getInstance().getUser().getFirstName().toUpperCase());
                                    if (counterProgressResult == 100) {
                                        Snackbar.make(resultProgBar, "You did amazing in this quiz. Well done!", Snackbar.LENGTH_LONG).show();
                                        animAllAnswersCorrect();
                                    }
                                }
                            }
                        });
                        counterProgressResult++;
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            resultComment.setText("KEEP TRYING " + CurrentUser.getInstance().getUser().getFirstName().toUpperCase());
        }

        int newPoints = CurrentUser.getInstance().getUser().getPoints() + QuizHolder.getInstance().getPoints();
        CurrentUser.getInstance().getUser().setPoints(newPoints);
        CurrentUser.getInstance().getUser().updatePoints();
        levelProgBar.setMax(100);
        levelProgBar.setProgress(new Rankings().getProgressPercent(CurrentUser.getInstance().getUser()));

        levelInDigits.setText(levelProgBar.getProgress() + "%");
        levelTxt.setText(new Rankings().getRanking(CurrentUser.getInstance().getUser()).getName());
        addToPrevResult();
        menuBtn.setOnClickListener(this);
        reportBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == menuBtn) {
            Rankings rankings = new Rankings();
            String ran = rankings.getRanking(CurrentUser.getInstance().getUser()).getName();
            if (ran.equalsIgnoreCase("noob")) {
                moveToMain();
            } else {
                showRatingDialog();
            }
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
        final Button submitBtn = feedBackDialog.findViewById(R.id.ok_button);
        final Button cancelBtn = feedBackDialog.findViewById(R.id.cancel_button);
        feedBackName.setText(CurrentUser.getInstance().getUser().getFirstName() + " " + CurrentUser.getInstance().getUser().getLastName());
        feedBackName.setEnabled(false);
        feedbackEmail.setText(CurrentUser.getInstance().getUser().getEmail());
        feedbackEmail.setEnabled(false);

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
                String content = feedbackContent.getText().toString();
                if (content.isEmpty()) {
                    Toast toast = Toast.makeText(context, "Please provide us with your opinions!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    //TODO: Take the information and send it via mail
                    Toast.makeText(context, "Thank you for giving your feedback!", Toast.LENGTH_LONG).show();
                    feedBackDialog.dismiss();
                }
            }
        });

        feedBackDialog.show();
    }

    private void showRatingDialog() {
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rating_feedback_alert_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final ImageButton one_rate_icon = dialog.findViewById(R.id.one_rate_icon);
        final ImageButton two_rate_icon = dialog.findViewById(R.id.two_rate_icon);
        final ImageButton three_rate_icon = dialog.findViewById(R.id.three_rate_icon);
        final ImageButton four_rate_icon = dialog.findViewById(R.id.four_rate_icon);
        final ImageButton five_rate_icon = dialog.findViewById(R.id.five_rate_icon);
        final Button cancelButton = dialog.findViewById(R.id.cancelBtn);
        dialog.setCancelable(false);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelButton.startAnimation(animation);
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

        dialog.show();
    }

    public void moveToMain() {
        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Timer timer = new Timer(true);
        final long PERIOD = 60 * 1000 * 5;
        Date timeout = new Date(t + PERIOD);
        DisabledQuizzes.getInstance().addQuiz(QuizHolder.getInstance().getQuiz(), timeout);
        timer.schedule(new TimerTask() {
            Quiz quiz = QuizHolder.getInstance().getQuiz();

            @Override
            public void run() {
                DisabledQuizzes.getInstance().removeQuiz(quiz);
            }
        }, timeout);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void saveRatingToDB() {
        QuizHolder.getInstance().getQuiz().rate(CurrentUser.getInstance().getUser().getId(), rating);
        Toast.makeText(context, "Thank you for giving your feedback!", Toast.LENGTH_LONG).show();
    }

    public void addToPrevResult() {
        String result = totalRightAnswers + "/" + totalQuestionNumbers;
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Result resultObject = new Result(quizTitle, date, result);
        counterbby = 0;
        saveResult(resultObject);
    }

    public void saveResult(Result resultObject) {

        File file = new File(context.getFilesDir(), "" + File.separator + "PREVRESULT.srl");
        boolean append = file.exists();

        try (
                FileOutputStream fout = new FileOutputStream(file, append);
                AppednableOOS oout = new AppednableOOS(fout, append);
        ) {
            oout.writeObject(resultObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void animAllAnswersCorrect() {
        new ParticleSystem(this, 20, R.drawable.amazing_icon, 5000)
                .setSpeedRange(0.02f, 0.4f)
                .oneShot(levelInDigits, 100);
    }
}
