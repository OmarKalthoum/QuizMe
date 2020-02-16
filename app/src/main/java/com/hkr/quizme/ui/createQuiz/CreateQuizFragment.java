package com.hkr.quizme.ui.createQuiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.hkr.quizme.R;

public class CreateQuizFragment extends Fragment {

    private CreateQuizViewModel createQuizViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        createQuizViewModel =
                ViewModelProviders.of(this).get(CreateQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_create_quiz, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        createQuizViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}