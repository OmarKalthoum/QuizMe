package com.hkr.quizme.ui.myQuizzes;

import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyQuizzesHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public Button deleteBtn;
    public RatingBar ratingBar;

    public MyQuizzesHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_text_view_my_quizzes);
        deleteBtn = itemView.findViewById(R.id.delete_button_my_quizzes);
        ratingBar = itemView.findViewById(R.id.ratingBar);


    }
}
