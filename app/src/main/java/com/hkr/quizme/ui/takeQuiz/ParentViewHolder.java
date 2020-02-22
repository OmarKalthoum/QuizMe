package com.hkr.quizme.ui.takeQuiz;

import android.view.View;
import android.widget.TextView;

import com.hkr.quizme.R;

public class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder{

    public TextView title;
    public ParentViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.question_review_text_view);
    }


}
