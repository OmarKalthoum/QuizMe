package com.hkr.quizme.ui.createQuiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.hkr.quizme.MainActivity;
import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Dialog extends DialogFragment implements View.OnClickListener {

    public RecyclerView recyclerView;
    private CreateQuizAdapter createQuizAdapter;
    private Button createQuizAfterReviewBtn;
    private int i = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.review_quiz_alert_dialog_layout, container, false);
        recyclerView = view.findViewById(R.id.review_recycler_view);
        createQuizAfterReviewBtn = view.findViewById(R.id.create_quiz_after_review);
        createQuizAfterReviewBtn.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        createQuizAdapter = new CreateQuizAdapter(this.getContext(), Question.getQuestions());
        recyclerView.setAdapter(createQuizAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (i > 0) {
            createQuizAdapter = new CreateQuizAdapter(this.getContext(), Question.getQuestions());
            recyclerView.setAdapter(createQuizAdapter);
        }
        i = 1;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().setCancelable(true);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = 900;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes(params);

    }

    @Override
    public void onClick(View v) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.blink_anim);
        if (v == createQuizAfterReviewBtn) {
            v.startAnimation(animation);
            if (Question.getQuestions().size() == 0) {
                Toast.makeText(getContext(), "Unable to create quiz since you did not add any question", Toast.LENGTH_LONG).show();
                getDialog().dismiss();

            } else {
                Question.getQuestions().clear();
                Toast.makeText(getContext(), "Your quiz has been created", Toast.LENGTH_LONG).show();
                //Todo: save the created quiz to the database. ALl the created question are saved inside
                // Question.getQuestions();
                // Every Question contains of number, questionTitle, two correct answers and four wrong answers
                // Loop the linkedlist Question.getQuestions and save every question in DB;

            }
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void not(int p) {
        createQuizAdapter = new CreateQuizAdapter(this.getContext(), Question.getQuestions());
        recyclerView.setAdapter(createQuizAdapter);
    }

}
