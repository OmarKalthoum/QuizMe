package com.hkr.quizme.ui.takeQuiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.hkr.quizme.R;
import com.hkr.quizme.database_utils.entities.Course;
import com.hkr.quizme.database_utils.entities.Subject;
import com.hkr.quizme.database_utils.tasks.GetCoursesTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TakeQuizFragment extends Fragment  {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Parent> parents;

    private TakeQuizViewModel takeQuizViewModel;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MyAdapter) recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        takeQuizViewModel = ViewModelProviders.of(this).get(TakeQuizViewModel.class);
        View root = inflater.inflate(R.layout.fragment_take_quiz, container, false);
        recyclerView = root.findViewById(R.id.RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter adapter = new MyAdapter(getContext(), initData());
        // TODO: FETCH SUBCATEGORIES FROM DB
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);
        return root;
    }

    private List<ParentObject> initData() {
        //ParentCreator parentCreator = ParentCreator.get(getContext());
        List<ParentObject> parentObjects = new ArrayList<>();
        GetCoursesTask task = new GetCoursesTask();
        try {
            List<Course> courses = task.execute().get();
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
            /*for (int i = 1; i < 10; i++) {
                Parent item = new Parent("Algorithm and Data Structure " + (i + 1));
                parents.add(item);
            }*/
        } catch (InterruptedException exception) {
            Log.d("DB:::::", exception.toString());
        } catch (ExecutionException exception) {
            Log.d("DB:::::", exception.toString());
        }
        return parentObjects;
        /*List<Parent> titles = new ArrayList<>();


        // TODO: GET SUBCATEGORIES SOM TILLHÖR QUIZ-CATEGORIES AND ADD THEM TO THE LIST BELOW
        for (Parent parent : titles) {
            List<Object> childList = new ArrayList<>();
            childList.add(new Child("asdasd"));
            childList.add(new Child("1111d"));
            parent.setChildObjectList(childList);
            parentObjects.add(parent);
        }
        return parentObjects;*/
    }


}