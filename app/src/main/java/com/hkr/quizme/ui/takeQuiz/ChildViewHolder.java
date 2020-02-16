package com.hkr.quizme.ui.takeQuiz;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hkr.quizme.R;

public class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder {

    public TextView textViewName;

    public ChildViewHolder(View itemView) {
        super(itemView);

        textViewName = itemView.findViewById(R.id.child_title);
    }
}
