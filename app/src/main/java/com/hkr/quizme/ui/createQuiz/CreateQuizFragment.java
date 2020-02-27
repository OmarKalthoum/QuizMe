package com.hkr.quizme.ui.createQuiz;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class CreateQuizFragment extends Fragment implements View.OnClickListener {

    private CreateQuizViewModel createQuizViewModel;
    private EditText correctAnswerOne, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour, correctAnswerTwo, question;
    private TextView questionNumber, addCorrectAnswerText, addWrongAnswerText;
    private Button finishCreating, nextQuestion;
    private ImageButton addCorrectAnswer, addWrongAnswer;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createQuizViewModel = ViewModelProviders.of(this).get(CreateQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        correctAnswerOne = root.findViewById(R.id.correct_answer_input);
        wrongAnswerOne = root.findViewById(R.id.wrong_answer_one);
        wrongAnswerTwo = root.findViewById(R.id.wrong_answer_two);
        wrongAnswerThree = root.findViewById(R.id.wrong_answer_three);
        question = root.findViewById(R.id.question_input);
        questionNumber = root.findViewById(R.id.question_amount);
        correctAnswerTwo = root.findViewById(R.id.correct_answer_two);
        wrongAnswerFour = root.findViewById(R.id.wrong_answer_four);

        addCorrectAnswer = root.findViewById(R.id.add_correct_answer);
        addWrongAnswer = root.findViewById(R.id.add_wrong_answer);
        finishCreating = root.findViewById(R.id.finish_create_quiz);
        nextQuestion = root.findViewById(R.id.done);
        addCorrectAnswerText = root.findViewById(R.id.add_correct_answer_text);
        addWrongAnswerText = root.findViewById(R.id.add_wrong_answer_text);

        addCorrectAnswer.setOnClickListener(this);
        addWrongAnswer.setOnClickListener(this);
        nextQuestion.setOnClickListener(this);
        finishCreating.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_anim);
        if (v == nextQuestion) {
            v.startAnimation(animation);
            //TODO: save the question with the corresponding alternatives and reload the fragment to create the next question


            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(this).attach(this).commit();
            return;
        }
        if (v == addCorrectAnswer) {
            addCorrectAnswer.setVisibility(View.INVISIBLE);
            addCorrectAnswerText.setVisibility(View.INVISIBLE);
            correctAnswerTwo.setVisibility(View.VISIBLE);

        }
        if (v == addWrongAnswer) {
            addWrongAnswer.setVisibility(View.INVISIBLE);
            addWrongAnswerText.setVisibility(View.INVISIBLE);
            wrongAnswerFour.setVisibility(View.VISIBLE);
        }
        if (v == finishCreating) {
            v.startAnimation(animation);
            showDialog();
        }

    }

    public void showDialog() {
        final Dialog dialog = new Dialog();
        dialog.show(getFragmentManager(), "Quiz");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}