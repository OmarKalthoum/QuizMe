package com.hkr.quizme;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hkr.quizme.database_utils.entities.Answer;
import com.hkr.quizme.global_data.QuizHolder;
import com.hkr.quizme.ui.chooseQuiz.ChooseQuiz;
import com.hkr.quizme.timers.UpdatingTimer;
import com.hkr.quizme.timers.TimerListener;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import static com.hkr.quizme.StartActivity.counterbby;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener, TimerListener {
    private final int TIME_TO_ANSWER = 40;
    private Button button1, button2, button3, button4;
    private TextView timer, totalQuestions, question;
    private UpdatingTimer questionTimer;
    private Handler handler;
    public static int totalQuestionNumbers;
    public static int totalRightAnswers;


    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (counterbby == 0) {
            totalQuestionNumbers = QuizHolder.getInstance().getMaxPoints();
            totalRightAnswers = totalQuestionNumbers;
            counterbby++;
        }

        // get the answers of current question
        if (!QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).fetchAnswers()) {
            Toast.makeText(this, "An error occurred, please try again.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ChooseQuiz.class);
            startActivity(intent);
        }

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);

        timer = findViewById(R.id.timer);
        totalQuestions = findViewById(R.id.totalQuestions);
        question = findViewById(R.id.question);

        totalQuestions.setText(String.format("%d/%d", QuizHolder.getInstance().getCurrentQuestion() + 1, QuizHolder.getInstance().getMaxPoints()));


        question.setText(QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).getQuestion());
        List<Answer> answers = QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).getAnswers();
        button1.setText(QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).getAnswers().get(0).getAnswer());
        button2.setText(QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).getAnswers().get(1).getAnswer());
        if (answers.size() >= 3) {
            button3.setText(QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).getAnswers().get(2).getAnswer());
            if (answers.size() == 4) {
                button4.setText(QuizHolder.getInstance().getQuiz().getQuestions().get(QuizHolder.getInstance().getCurrentQuestion()).getAnswers().get(3).getAnswer());
            } else {
                button4.setVisibility(View.INVISIBLE);
                button4.setClickable(false);
            }
        } else {
            button3.setVisibility(View.INVISIBLE);
            button3.setClickable(false);
            button4.setVisibility(View.INVISIBLE);
            button4.setClickable(false);
        }

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        timer.setText(Integer.toString(TIME_TO_ANSWER));
        handler = new Handler();
        questionTimer = new UpdatingTimer(this, TIME_TO_ANSWER);

        new Thread(questionTimer).start();
    }

    @Override
    public void onClick(View v) {
        TransitionDrawable transition = (TransitionDrawable) v.getBackground();
        transition.startTransition(200);

        if (v == button1) {
            if (!QuizHolder.getInstance().registerUserAnswer(0)) {
                transitionWrongAnswer(v);

            }
            setTransitionGrey(button2);
            setTransitionGrey(button3);
            setTransitionGrey(button4);
        }
        if (v == button2) {

            setTransitionGrey(button1);
            setTransitionGrey(button3);
            setTransitionGrey(button4);
            if (!QuizHolder.getInstance().registerUserAnswer(1)) {
                transitionWrongAnswer(v);
            }
        }
        if (v == button3) {
            setTransitionGrey(button2);
            setTransitionGrey(button1);
            setTransitionGrey(button4);
            if (!QuizHolder.getInstance().registerUserAnswer(2)) {
                transitionWrongAnswer(v);
            }
        }
        if (v == button4) {
            setTransitionGrey(button2);
            setTransitionGrey(button1);
            setTransitionGrey(button3);
            if (!QuizHolder.getInstance().registerUserAnswer(3)) {
                transitionWrongAnswer(v);
            }
        }
        questionTimer.stop();
    }

    public void setTransitionGrey(Button button) {
        button.setBackground(getDrawable(R.drawable.transition_button_grey));
        TransitionDrawable transition2 = (TransitionDrawable) button.getBackground();
        transition2.startTransition(200);
        button.setClickable(false);
    }

    @Override
    public synchronized void onTimerUpdate(final int timeLeft) {
        handler.post(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                timer.setText(Integer.toString(timeLeft));
            }
        });
    }

    @Override
    public synchronized void onTimerStop() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                QuizHolder.getInstance().incrementCurrentQuestion();
                if (QuizHolder.getInstance().getCurrentQuestion() >= QuizHolder.getInstance().getMaxPoints()) {
                    Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(QuizActivity.this, QuizActivity.class);
                    startActivity(intent);
                }
            }
        }, 700);
    }

    @Override
    public void onBackPressed() {
        warningDialog();
    }

    public void warningDialog() {
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
                Intent f = new Intent(getBaseContext(), MainActivity.class);
                startActivity(f);
                warningDialog.dismiss();
            }
        });

        warningDialog.show();
    }


    public void transitionWrongAnswer(View v) {
        totalRightAnswers--;
        v.setBackground(getDrawable(R.drawable.transition_button_red));
        TransitionDrawable transition3 = (TransitionDrawable) v.getBackground();
        transition3.startTransition(200);
        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        assert vibe != null;
        vibe.vibrate(200);
    }

}
