package com.hkr.quizme.ui.createQuiz;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.appcompat.app.AppCompatActivity;

import static com.hkr.quizme.ui.createQuiz.CreateQuizAdapter.questions;

public class EditQuestion extends AppCompatActivity {
    private Button done;
    private TextView questionNumber;
    private EditText question, correctAnswerOne, correctAnswerTwo, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);

        question = findViewById(R.id.question_input);
        correctAnswerOne = findViewById(R.id.correct_answer_input);
        correctAnswerTwo = findViewById(R.id.correct_answer_two);
        wrongAnswerOne = findViewById(R.id.wrong_answer_one);
        wrongAnswerTwo = findViewById(R.id.wrong_answer_two);
        wrongAnswerThree = findViewById(R.id.wrong_answer_three);
        wrongAnswerFour = findViewById(R.id.wrong_answer_four);
        questionNumber = findViewById(R.id.question_amount);
        done = findViewById(R.id.done);


        question.setText(getIntent().getStringExtra("question"));
        correctAnswerOne.setText(getIntent().getStringExtra("correctAnswerOne"));
        correctAnswerTwo.setText(getIntent().getStringExtra("correctAnswerTwo"));
        wrongAnswerOne.setText(getIntent().getStringExtra("wrongAnswerOne"));
        wrongAnswerTwo.setText(getIntent().getStringExtra("wrongAnswerTwo"));
        wrongAnswerThree.setText(getIntent().getStringExtra("wrongAnswerThree"));
        wrongAnswerFour.setText(getIntent().getStringExtra("wrongAnswerFour"));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setQuestion(question.getText().toString());
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setCorrectAnswerOne(correctAnswerOne.getText().toString());
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setCorrectAnswerTwo(correctAnswerTwo.getText().toString());
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setWrongAnswerOne(wrongAnswerOne.getText().toString());
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setWrongAnswerTwo(wrongAnswerTwo.getText().toString());
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setWrongAnswerThree(wrongAnswerThree.getText().toString());
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setWrongAnswerFour(wrongAnswerFour.getText().toString());
                finish();
            }
        });
    }
}
