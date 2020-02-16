package com.hkr.quizme.ui.takeQuiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.hkr.quizme.R;

import java.util.List;


public class MyAdapter extends ExpandableRecyclerAdapter<ParentViewHolder, ChildViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;

    public MyAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.parent_layout_list,viewGroup,false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.child_layout_list,viewGroup,false);
        view.setOnClickListener(this);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ParentViewHolder parentViewHolder, int i, Object o) {
        Parent title = (Parent)o;
        parentViewHolder.title.setText(title.getName());

    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, Object o) {
        Child title = (Child)o;
        childViewHolder.textViewName.setText(title.getName());

    }

    @Override
    public void onClick(View v) {
        // TODO : START NEW ACTIVITY
        Toast.makeText(v.getContext(), "Hej",Toast.LENGTH_LONG).show();
    }
}