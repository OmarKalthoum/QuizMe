package com.hkr.quizme.ui.myQuizzes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hkr.quizme.R;
import com.hkr.quizme.ui.previousResult.PrevResultAdapter;

import java.util.LinkedList;

public class MyQuizzesFragment extends Fragment {

    private LinkedList<MyQuizzes> myQuizzes;
    private TextView textStatus;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_quizzes, container, false);

        textStatus = root.findViewById(R.id.text_status_my_quizzes);
        recyclerView = root.findViewById(R.id.recyclerView_my_quizzes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyQuizzesAdapter myQuizzesAdapter = new MyQuizzesAdapter(getContext(), initData());
        myQuizzesAdapter.setHasStableIds(true);
        recyclerView.setAdapter(myQuizzesAdapter);

        return root;
    }

    public LinkedList<MyQuizzes> initData() {
        //TODO:: Get the correct values from the DB
        myQuizzes = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            myQuizzes.add(new MyQuizzes("Algorithm and data structure", "10/1" + i));
        }

        return myQuizzes;
    }
}