package com.hkr.quizme.ui.takeQuiz;

import android.content.Context;
import android.util.Log;

import com.hkr.quizme.database_utils.APICommunicator;
import com.hkr.quizme.database_utils.entities.Course;
import com.hkr.quizme.database_utils.entities.Subject;
import com.hkr.quizme.database_utils.tasks.GetCoursesTask;

import java.util.ArrayList;
import java.util.List;

public class ParentCreator {
    static ParentCreator parentCreator;
    List<Parent> parents;

    public ParentCreator(Context context) {
        parents = new ArrayList<>();
        // TODO : GET QUIZ-CATEGORIES FROM DATABASE AND ADD THEM TO THE LIST BELOW
        GetCoursesTask task = new GetCoursesTask();
        try {
            List<Course> courses = task.execute().get();
            for (Course course : courses) {
                Parent item = new Parent(course.getName());
                List<Object> subjects = new ArrayList<>();
                for (Subject subject : course.getSubjects()) {
                    subjects.add(subject);
                }
                item.setChildObjectList(subjects);
                parents.add(item);
            }
            /*for (int i = 1; i < 10; i++) {
                Parent item = new Parent("Algorithm and Data Structure " + (i + 1));
                parents.add(item);
            }*/
        } catch (Exception exception) {
            Log.d("DB:::::", "Fuck.");
        }
    }

    public static ParentCreator get(Context context) {
        if (parentCreator == null) {
            parentCreator = new ParentCreator(context);
        }
        return parentCreator;
    }

    public List<Parent> getAll() {
        return parents;
    }
}
