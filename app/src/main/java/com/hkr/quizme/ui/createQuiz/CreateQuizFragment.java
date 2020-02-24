package com.hkr.quizme.ui.createQuiz;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

public class CreateQuizFragment extends Fragment implements View.OnClickListener {

    private CreateQuizViewModel createQuizViewModel;
    private EditText correctAnswerOne, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour, correctAnswerTwo, question;
    private TextView questionNumber, addCorrectAnswerText, addWrongAnswerText;
    private Button finishCreating, nextQuestion;
    private ImageButton addCorrectAnswer, addWrongAnswer;
    public static FragmentManager fragmentManager2;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        createQuizViewModel = ViewModelProviders.of(this).get(CreateQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_quiz, container, false);

        fragmentManager2 = getFragmentManager();

        correctAnswerOne = root.findViewById(R.id.correct_answer_input);
        wrongAnswerOne = root.findViewById(R.id.wrong_answer_one);
        wrongAnswerTwo = root.findViewById(R.id.wrong_answer_two);
        wrongAnswerThree = root.findViewById(R.id.wrong_answer_three);
        question = root.findViewById(R.id.question_input);
        questionNumber = root.findViewById(R.id.question_amount);
        addCorrectAnswer = root.findViewById(R.id.add_correct_answer);
        addWrongAnswer = root.findViewById(R.id.add_wrong_answer);
        finishCreating = root.findViewById(R.id.finish_create_quiz);
        nextQuestion = root.findViewById(R.id.done);
        addCorrectAnswerText = root.findViewById(R.id.add_correct_answer_text);
        addWrongAnswerText = root.findViewById(R.id.add_wrong_answer_text);
        correctAnswerTwo = root.findViewById(R.id.correct_answer_two);
        wrongAnswerFour = root.findViewById(R.id.wrong_answer_four);

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
            //TODO: save the question with the corresponding alternatives and move to next intent to create the next question
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
            showDialog(getFragmentManager());
        }

    }

    public void showDialog( FragmentManager fragmentManager) {

        final Dialog dialog = new Dialog();
        dialog.show(fragmentManager, "Quiz");
    }
}