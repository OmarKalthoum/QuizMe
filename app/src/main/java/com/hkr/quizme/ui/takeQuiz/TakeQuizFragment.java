package com.hkr.quizme.ui.takeQuiz;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.hkr.quizme.R;
import com.hkr.quizme.database_utils.entities.Course;
import com.hkr.quizme.database_utils.entities.Subject;
import com.hkr.quizme.database_utils.tasks.GetCoursesTask;
import com.hkr.quizme.ui.chooseQuiz.ChooseQuizFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class TakeQuizFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Parent> parents;
    private TextView statusTextView;
    private LinearLayout linearLayout;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MyAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_take_quiz, container, false);
        statusTextView = root.findViewById(R.id.text_status);
        recyclerView = root.findViewById(R.id.recycleview_choose_quiz);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter(getContext(), initData());
        // TODO: FETCH SUBCATEGORIES FROM DB
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);
        return root;
    }

    private List<ParentObject> initData() {
        List<ParentObject> parentObjects = new ArrayList<>();
        GetCoursesTask task = new GetCoursesTask();
        try {
            List<Course> courses = task.execute(statusTextView).get();
            if (courses != null) {
                for (Course course : courses) {
                    Parent item = new Parent(course.getName());
                    List<Object> subjects = new ArrayList<>();
                    for (Subject subject : course.getSubjects()) {
                        Child child = new Child(subject.getId(), subject.getName());
                        subjects.add(child);
                    }
                    item.setChildObjectList(subjects);
                    parentObjects.add(item);
                }
            }
        } catch (InterruptedException | ExecutionException exception) {
            Log.d("DB:::::", exception.toString());
        }

        return parentObjects;
    }



}