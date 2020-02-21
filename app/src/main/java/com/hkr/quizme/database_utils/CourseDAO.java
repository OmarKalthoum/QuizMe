package com.hkr.quizme.database_utils;

import android.util.Log;

import com.hkr.quizme.database_utils.entities.Course;
import com.hkr.quizme.database_utils.entities.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements DAO<Course> {
    @Override
    public JSONObject updateInternal(Course object) {
        return null;
    }

    @Override
    public JSONObject updateExternal(Course object) {
        return null;
    }

    @Override
    public void insert(Course object) {

    }

    public List<Course> getCourses() {
        APICommunicator communicator = new APICommunicator();
        JSONObject params = new JSONObject();
        JSONObject response = communicator.apiCallForResponse("/courses", "POST", params);
        Log.d("JSON::::::", response.toString());
        List<Course> result = new ArrayList<>();
        try {
            JSONArray courses = response.getJSONArray("courses");
            for (int i = 0; i < courses.length(); i++) {
                JSONObject object = courses.getJSONObject(i);
                Course course = new Course(
                        object.getInt("courseId"),
                        object.getString("courseName"),
                        object.getString("courseCode")
                );
                List<Subject> subjects = new ArrayList<>();
                JSONArray jsonSubjects = object.getJSONArray("subjects");
                for (int j = 0; j < jsonSubjects.length(); j++) {
                    JSONObject jsonSubject = jsonSubjects.getJSONObject(j);
                    Subject subject = new Subject(
                            jsonSubject.getInt("id"),
                            jsonSubject.getString("name")
                    );
                    subjects.add(subject);
                }
                course.setSubjects(subjects);
                result.add(course);
            }
            return result;
        } catch (JSONException exception) {
            Log.d("JSON::::::", exception.toString());
        }
        return null; // TODO: fucking dont do this
    }
}
