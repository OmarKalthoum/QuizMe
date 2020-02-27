package com.hkr.quizme.ui.createQuiz;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.hkr.quizme.R;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateQuizAdapter extends RecyclerView.Adapter<CreateQuizHolder> {
    private Context context;
    public static LinkedList<Question> questions;
    private Button editQuestion, deleteQuestion;
    private Animation animation;


    public CreateQuizAdapter(Context context, LinkedList<Question> questions) {
        this.context = context;
        this.questions = questions;
        animation = AnimationUtils.loadAnimation(context, R.anim.blink_anim);
    }

    @NonNull
    @Override
    public CreateQuizHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_quiz_card_view, parent, false);
        editQuestion = v.findViewById(R.id.edit_question_button);
        deleteQuestion = v.findViewById(R.id.delete_question_button);

        CreateQuizHolder createQuizHolder = new CreateQuizHolder(v);
        return createQuizHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CreateQuizHolder holder, final int position) {
        holder.question.setText(questions.get(position).getQuestion());
        holder.correctAnswerOne.setText(questions.get(position).getCorrectAnswerOne());
        holder.wrongAnswerOne.setText(questions.get(position).getWrongAnswerOne());
        holder.wrongAnswerTwo.setText(questions.get(position).getWrongAnswerTwo());
        holder.wrongAnswerThree.setText(questions.get(position).getWrongAnswerThree());
        holder.correctAnswerTwo.setText(questions.get(position).getCorrectAnswerTwo());
        holder.wrongAnswerFour.setText(questions.get(position).getWrongAnswerFour());

        editQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                Intent intent = new Intent(context, EditQuestion.class);
                intent.putExtra("question", questions.get(position).getQuestion());
                intent.putExtra("correctAnswerOne", questions.get(position).getCorrectAnswerOne());
                intent.putExtra("wrongAnswerOne", questions.get(position).getWrongAnswerOne());
                intent.putExtra("wrongAnswerTwo", questions.get(position).getWrongAnswerTwo());
                intent.putExtra("wrongAnswerThree", questions.get(position).getWrongAnswerThree());
                intent.putExtra("correctAnswerTwo", questions.get(position).getCorrectAnswerTwo());
                intent.putExtra("wrongAnswerFour", questions.get(position).getWrongAnswerFour());
                intent.putExtra("position", String.valueOf(position));

                context.startActivity(intent);

            }
        });
        deleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                questions.remove(position);
                // TODO: call the dialog again
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

}
