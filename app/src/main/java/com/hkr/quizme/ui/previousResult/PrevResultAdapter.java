package com.hkr.quizme.ui.previousResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkr.quizme.R;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrevResultAdapter extends RecyclerView.Adapter<PrevResultHolder> {
    private Context context;
    private LinkedList<Result> prevResult;

    public PrevResultAdapter(Context context, LinkedList<Result> prevResult) {
        this.context = context;
        this.prevResult = prevResult;
    }

    @NonNull
    @Override
    public PrevResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prev_result_parent_layout_list, parent, false);
        PrevResultHolder prevResultHolder = new PrevResultHolder(v);
        return prevResultHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrevResultHolder holder, int position) {
        holder.title.setText(prevResult.get(position).getTitle());
        holder.date.setText(prevResult.get(position).getDate());
        holder.result.setText(prevResult.get(position).getResult());
    }

    @Override
    public int getItemCount() {
        return prevResult.size();
    }
}
