package com.hkr.quizme.ui.myQuizzes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.hkr.quizme.R;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyQuizzesAdapter extends RecyclerView.Adapter<MyQuizzesHolder> {

    private Context context;
    private LinkedList<MyQuizzes> myQuizzes;
    private Animation animation;


    public MyQuizzesAdapter(Context context, LinkedList<MyQuizzes> myQuizzes) {
        this.context = context;
        this.myQuizzes = myQuizzes;
        animation = AnimationUtils.loadAnimation(context, R.anim.blink_anim);
    }

    @NonNull
    @Override
    public MyQuizzesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_quizzes_parent_layout_list, parent, false);
        MyQuizzesHolder myQuizzesHolder = new MyQuizzesHolder(v);
        return myQuizzesHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyQuizzesHolder holder, int position) {
        holder.title.setText(myQuizzes.get(position).getTitle());
        holder.rating.setText(holder.rating.getText() + myQuizzes.get(position).getRating());
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                //TODO:: DELETE the chosen quiz
            }
        });
    }

    @Override
    public int getItemCount() {
        return myQuizzes.size();
    }
}
