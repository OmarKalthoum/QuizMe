package com.hkr.quizme.ui.createQuiz;

import android.view.View;
import android.widget.TextView;

import com.hkr.quizme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateQuizHolder extends RecyclerView.ViewHolder {
    public TextView question, correctAnswerOne,correctAnswerTwo, wrongAnswerOne, wrongAnswerTwo, wrongAnswerThree, wrongAnswerFour;

    public CreateQuizHolder(@NonNull View itemView) {
        super(itemView);

        question = itemView.findViewById(R.id.question_review_text_view);
        correctAnswerOne = itemView.findViewById(R.id.altOne);
        wrongAnswerOne = itemView.findViewById(R.id.altTwo);
        wrongAnswerTwo = itemView.findViewById(R.id.altThree);
        wrongAnswerThree = itemView.findViewById(R.id.altFour);
        correctAnswerTwo = itemView.findViewById(R.id.altFive);
        wrongAnswerFour = itemView.findViewById(R.id.altSix);

    }
}
