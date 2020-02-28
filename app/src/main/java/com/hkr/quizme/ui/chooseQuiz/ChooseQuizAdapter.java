package com.hkr.quizme.ui.chooseQuiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkr.quizme.QuizActivity;
import com.hkr.quizme.R;
import com.hkr.quizme.global_data.QuizHolder;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseQuizAdapter extends RecyclerView.Adapter<ChooseQuizHolder> {

    private LinkedList<ChooseQuiz> chooseQuizLinkedList;
    private Context context;

    public ChooseQuizAdapter(LinkedList<ChooseQuiz> chooseQuizLinkedList, Context context) {
        this.chooseQuizLinkedList = chooseQuizLinkedList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChooseQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_quiz_parent_layout_list, parent, false);
        ChooseQuizHolder chooseQuizHolder = new ChooseQuizHolder(v);
        return chooseQuizHolder;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ChooseQuizHolder holder, final int position) {
        holder.title.setText(chooseQuizLinkedList.get(position).getTitle());
        holder.rating.setText(String.format("%.1f/5", chooseQuizLinkedList.get(position).getRating()));
        holder.pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Running quiz::", Integer.toString(chooseQuizLinkedList.get(position).getId()));
                QuizHolder.getInstance().initialize(chooseQuizLinkedList.get(position).getId());
                Intent intent = new Intent(v.getContext(), QuizActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chooseQuizLinkedList.size();
    }
}