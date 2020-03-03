package com.hkr.quizme.ui.myQuizzes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
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
import com.hkr.quizme.database_utils.entities.Quiz;

import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyQuizzesAdapter extends RecyclerView.Adapter<MyQuizzesHolder> {

    private Context context;
    private ArrayList<Quiz> myQuizzes;
    private Animation animation;


    public MyQuizzesAdapter(Context context, ArrayList<Quiz> myQuizzes) {
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyQuizzesHolder holder, final int position) {
        holder.title.setText(myQuizzes.get(position).getName());
        holder.rating.setText(Double.toString(myQuizzes.get(position).getRating()) + "/5");
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                alertDialog(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myQuizzes.size();
    }

    public void alertDialog(final int pos) {
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
               if ( myQuizzes.get(pos).removeQuiz()) {
                   Toast.makeText(context, "Your quiz has been removed", Toast.LENGTH_LONG).show();
                   myQuizzes.remove(pos);
                   notifyDataSetChanged();
               } else {
                   Toast.makeText(context, "Something went wrong when deleting the quiz...", Toast.LENGTH_LONG).show();
               }
                warningDialog.dismiss();
            }
        });

        warningDialog.show();
    }
}
