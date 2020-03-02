package com.hkr.quizme.database_utils.entities;

import android.os.AsyncTask;
import android.util.Log;

import com.hkr.quizme.database_utils.CourseDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Course {
    private int id;
    private String name;
    private String code;
    private List<Subject> subjects;

    public Course(int id, String name, String code, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.subjects = subjects;
    }

    public Course(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public static List<Course> getCourses() {
        try {
            return new GetCoursesSimpleTask().execute().get();
        } catch (ExecutionException | InterruptedException exception) {
            Log.e("Course::", exception.toString());
            return new ArrayList<>();
        }
    }

    private static class GetCoursesSimpleTask extends AsyncTask<Void, Void, List<Course>> {
        @Override
        protected List<Course> doInBackground(Void... voids) {
            CourseDAO dao  = new CourseDAO();
            return dao.getCourses();
        }
    }
}
