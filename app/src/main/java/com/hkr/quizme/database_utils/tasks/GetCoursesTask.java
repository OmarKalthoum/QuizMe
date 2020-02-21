package com.hkr.quizme.database_utils.tasks;

import android.os.AsyncTask;

import com.hkr.quizme.database_utils.CourseDAO;
import com.hkr.quizme.database_utils.entities.Course;

import java.util.List;

public class GetCoursesTask extends AsyncTask<Void, Void, List<Course>> {
    @Override
    protected List<Course> doInBackground(Void... voids) {
        CourseDAO dao = new CourseDAO();
        return dao.getCourses();
    }
}
