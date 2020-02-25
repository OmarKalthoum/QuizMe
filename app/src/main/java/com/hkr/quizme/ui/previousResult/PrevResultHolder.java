package com.hkr.quizme.ui.previousResult;

import android.view.View;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrevResultHolder extends RecyclerView.ViewHolder {
    public TextView title, date, result;

    public PrevResultHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_text_view_my_quizzes);
        date = itemView.findViewById(R.id.rating_text_view_choose_quiz);
        result = itemView.findViewById(R.id.delete_button_my_quizzes);

    }
}
