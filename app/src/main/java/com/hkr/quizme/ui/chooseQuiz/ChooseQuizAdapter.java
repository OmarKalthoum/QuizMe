package com.hkr.quizme.ui.chooseQuiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkr.quizme.R;

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

    @Override
    public void onBindViewHolder(@NonNull ChooseQuizHolder holder, int position) {
        holder.title.setText(chooseQuizLinkedList.get(position).getTitle());
        holder.rating.setText(chooseQuizLinkedList.get(position).getRating());
        holder.pos = position;
    }

    @Override
    public int getItemCount() {
        return chooseQuizLinkedList.size();
    }


}
