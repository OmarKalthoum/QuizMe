package com.hkr.quizme.ui.chooseQuiz;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hkr.quizme.QuizActivity;
import com.hkr.quizme.R;
import com.hkr.quizme.global_data.QuizHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseQuizHolder extends RecyclerView.ViewHolder {

    public TextView title, rating;
    public int pos;

    private LinearLayout linearLayout;
    public ChooseQuizHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_text_view_choose_quiz);
        rating = itemView.findViewById(R.id.rating_text_view_choose_quiz);
        linearLayout = itemView.findViewById(R.id.clickCardView);
    }
}
