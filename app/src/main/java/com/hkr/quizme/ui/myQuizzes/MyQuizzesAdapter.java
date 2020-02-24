package com.hkr.quizme.ui.myQuizzes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hkr.quizme.R;

import java.util.LinkedList;
import java.util.Objects;

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
                alertDialog();

            }
        });
    }

    @Override
    public int getItemCount() {
        return myQuizzes.size();
    }

    public void alertDialog() {
        final Dialog warningDialog = new Dialog(context);
        warningDialog.setContentView(R.layout.warning_alert_dialog);
        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final Button okBtn = warningDialog.findViewById(R.id.ok_button);
        final Button cancelBtn = warningDialog.findViewById(R.id.cancel_button);
        final TextView title = warningDialog.findViewById(R.id.title_dialog);
        title.setText("Are you sure you want to delete the quiz?");
        warningDialog.setCancelable(false);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                warningDialog.dismiss();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:::: reomve the chosen quiz
                Toast.makeText(context, "Your quiz has been removed", Toast.LENGTH_LONG).show();
                warningDialog.dismiss();
            }
        });

        warningDialog.show();
    }
}
