package com.hkr.quizme.ui.chooseQuiz;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChooseQuizHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public int pos;
    public RatingBar ratingBar;

    public ChooseQuizHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_text_view_choose_quiz);
        ratingBar = itemView.findViewById(R.id.ratingBarChooseQuiz);


    }


}
