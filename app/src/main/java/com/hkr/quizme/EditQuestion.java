package com.hkr.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hkr.quizme.ui.createQuiz.CreateQuizAdapter;
import com.hkr.quizme.ui.createQuiz.CreateQuizFragment;

import java.util.Objects;

import static com.hkr.quizme.ui.createQuiz.CreateQuizAdapter.questions;
import static com.hkr.quizme.ui.createQuiz.CreateQuizFragment.fragmentManager2;

public class EditQuestion extends AppCompatActivity {
    private Button done;
    private TextView questionNumber;
    private EditText question, correctAnswerOne, correctAnswerTwo, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour;
    //private final int position = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("position")));


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
                Log.d("OMAR:", getIntent().getStringExtra("position"));
                questions.get(Integer.parseInt(getIntent().getStringExtra("position"))).setQuestion(question.getText().toString());
                /*CreateQuizFragment createQuizFragment = new CreateQuizFragment();
                createQuizFragment.showDialog(fragmentManager2);*/
            }
        });
    }
}
