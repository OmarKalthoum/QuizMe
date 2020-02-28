package com.hkr.quizme.ui.createQuiz;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hkr.quizme.R;

import java.util.LinkedList;

import androidx.appcompat.app.AppCompatActivity;

public class EditQuestion extends AppCompatActivity {
    private Button done;
    private TextView questionNumber;
    private EditText question, correctAnswerOne, correctAnswerTwo, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour;
    LinkedList<Question> questions = Question.getQuestions();


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
        final int pos = getIntent().getIntExtra("position", 0);

        question.setText(questions.get(pos).getQuestion());
        correctAnswerOne.setText(questions.get(pos).getCorrectAnswerOne());
        correctAnswerTwo.setText(questions.get(pos).getCorrectAnswerTwo());
        wrongAnswerOne.setText(questions.get(pos).getWrongAnswerOne());
        wrongAnswerTwo.setText(questions.get(pos).getWrongAnswerTwo());
        wrongAnswerThree.setText(questions.get(pos).getWrongAnswerThree());
        wrongAnswerFour.setText(questions.get(pos).getWrongAnswerFour());
        questionNumber.setText("Question: " + questions.get(pos).getQuestionNumber());

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                questions.get(pos).setQuestion(question.getText().toString());
                questions.get(pos).setCorrectAnswerOne(correctAnswerOne.getText().toString());
                questions.get(pos).setCorrectAnswerTwo(correctAnswerTwo.getText().toString());
                questions.get(pos).setWrongAnswerOne(wrongAnswerOne.getText().toString());
                questions.get(pos).setWrongAnswerTwo(wrongAnswerTwo.getText().toString());
                questions.get(pos).setWrongAnswerThree(wrongAnswerThree.getText().toString());
                questions.get(pos).setWrongAnswerFour(wrongAnswerFour.getText().toString());
                finish();
            }
        });
    }
}
