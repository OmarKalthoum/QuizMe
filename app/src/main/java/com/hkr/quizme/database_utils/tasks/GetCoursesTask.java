package com.hkr.quizme.database_utils.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.hkr.quizme.database_utils.CourseDAO;
import com.hkr.quizme.database_utils.entities.Course;

import java.util.List;

public class GetCoursesTask extends AsyncTask<TextView, Void, List<Course>> {
    @Override
    protected List<Course> doInBackground(TextView... textViews) {
        CourseDAO dao = new CourseDAO();
        Log.d("DB::::::", String.format("%d", textViews.length));
        return dao.getCourses(textViews[0]);
    }
}
