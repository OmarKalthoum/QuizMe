package com.hkr.quizme.ui.myQuizzes;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyQuizzesHolder extends RecyclerView.ViewHolder {
    public TextView title, rating;
    public Button deleteBtn;

    public MyQuizzesHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_text_view_my_quizzes);
        rating = itemView.findViewById(R.id.rating_text_view_my_quizzes);
        deleteBtn = itemView.findViewById(R.id.delete_button_my_quizzes);

    }
}
