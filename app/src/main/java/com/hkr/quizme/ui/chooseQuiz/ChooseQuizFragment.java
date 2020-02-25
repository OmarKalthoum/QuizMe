package com.hkr.quizme.ui.chooseQuiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hkr.quizme.R;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseQuizFragment extends Fragment {


    private ChooseQuizViewModel chooseQuizViewModel;
    private LinkedList<ChooseQuiz> chooseQuizLinkedList;
    private TextView textStatus;
    private RecyclerView recyclerView;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chooseQuizViewModel = ViewModelProviders.of(this).get(ChooseQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_choose_quiz, container, false);


        textStatus = root.findViewById(R.id.text_status_choose_quiz);
        recyclerView = root.findViewById(R.id.recycleview_choose_quiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ChooseQuizAdapter chooseQuizAdapter = new ChooseQuizAdapter(initData(), getContext());
        chooseQuizAdapter.setHasStableIds(true);
        recyclerView.setAdapter(chooseQuizAdapter);


        return root;
    }

    public LinkedList<ChooseQuiz> initData() {
        //TODO:: Get the correct values from the DB
        chooseQuizLinkedList = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            chooseQuizLinkedList.add(new ChooseQuiz("Algorithm and data structure", "10/1" + i));
        }

        return chooseQuizLinkedList;
    }

}