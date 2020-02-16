package com.hkr.quizme.ui.takeQuiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.hkr.quizme.R;

import java.util.ArrayList;
import java.util.List;

public class TakeQuizFragment extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Parent> parents;

    private TakeQuizViewModel takeQuizViewModel;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MyAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        takeQuizViewModel = ViewModelProviders.of(this).get(TakeQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_take_quiz, container, false);
        recyclerView = root.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter(getContext(), initData());
        // TODO: FETCH SUBCATEGORIES FROM DB
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);
        return root;
    }

    private List<ParentObject> initData() {
        ParentCreator parentCreator = ParentCreator.get(getContext());
        List<Parent> titles = parentCreator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        // TODO: GET SUBCATEGORIES SOM TILLHÖR QUIZ-CATEGORIES AND ADD THEM TO THE LIST BELOW
        for (Parent parent : titles) {
            List<Object> childList = new ArrayList<>();
            childList.add(new Child("asdasd"));
            childList.add(new Child("1111d"));
            parent.setChildObjectList(childList);
            parentObjects.add(parent);
        }
        return parentObjects;
    }


}