package com.hkr.quizme.ui.chooseQuiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hkr.quizme.QuizActivity;
import com.hkr.quizme.R;
import com.hkr.quizme.database_utils.entities.Quiz;
import com.hkr.quizme.global_data.DisabledQuizzes;
import com.hkr.quizme.global_data.QuizHolder;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseQuizAdapter extends RecyclerView.Adapter<ChooseQuizHolder> {

    private LinkedList<ChooseQuiz> chooseQuizLinkedList;
    public static String quizTitle;
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
        holder.ratingBar.setRating((float) chooseQuizLinkedList.get(position).getRating());
        holder.pos = position;
        final Quiz quiz = new Quiz(chooseQuizLinkedList.get(position).getId(), chooseQuizLinkedList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DisabledQuizzes.getInstance().contains(quiz)) {
                    Log.d("Running quiz::", Integer.toString(chooseQuizLinkedList.get(position).getId()));
                    quizTitle = chooseQuizLinkedList.get(position).getTitle();
                    QuizHolder.getInstance().initialize(chooseQuizLinkedList.get(position).getId());
                    Intent intent = new Intent(v.getContext(), QuizActivity.class);
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), String.format("%s time until unlock...", DisabledQuizzes.getInstance().getTimeLeft(quiz)), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chooseQuizLinkedList.size();
    }


}
