package com.hkr.quizme.ui.takeQuiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.hkr.quizme.R;
import com.hkr.quizme.database_utils.entities.Subject;
import com.hkr.quizme.global_data.SubjectHolder;
import com.hkr.quizme.ui.chooseQuiz.ChooseQuizFragment;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class MyAdapter extends ExpandableRecyclerAdapter<ParentViewHolder, ChildViewHolder> implements View.OnClickListener {

    private LayoutInflater inflater;

    public MyAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.take_quiz_parent_layout_list, viewGroup, false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.take_quiz_child_layout_list, viewGroup, false);
        view.setOnClickListener(this);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindParentViewHolder(ParentViewHolder parentViewHolder, int i, Object o) {
        Parent title = (Parent) o;
        parentViewHolder.title.setText(title.getName());
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, Object o) {
        final Child title = (Child) o;
        childViewHolder.textViewName.setText(title.getName());
        childViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ChooseQuizFragment myFragment = new ChooseQuizFragment();
                SubjectHolder.getInstance().setSubject(new Subject(title.getId(), title.getName()));
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        // TODO : START NEW ACTIVITY

    }
}