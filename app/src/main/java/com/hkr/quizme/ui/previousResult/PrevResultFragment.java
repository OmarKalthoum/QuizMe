package com.hkr.quizme.ui.previousResult;

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

public class PrevResultFragment extends Fragment {

   // private PrevResultViewModel prevResultViewModel;
    private RecyclerView recyclerView;
    private TextView textStatus;
    private LinkedList<Result> results;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // prevResultViewModel = ViewModelProviders.of(this).get(PrevResultViewModel.class);

        View root = inflater.inflate(R.layout.fragment_prev_result, container, false);
        textStatus = root.findViewById(R.id.text_status_prev_result);
        recyclerView = root.findViewById(R.id.recyclerView_prev_result);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        PrevResultAdapter prevResultAdapter = new PrevResultAdapter(getContext(), initData());
        prevResultAdapter.setHasStableIds(true);
        recyclerView.setAdapter(prevResultAdapter);

        return root;
    }

    public LinkedList<Result> initData() {
        //TODO:: GEt the correct values from the DB
        results = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            results.add(new Result("Algorithm and data structure", "2020-02-1" + i, "8/1" + i));
        }

        return results;
    }
}