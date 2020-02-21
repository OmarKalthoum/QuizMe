package com.hkr.quizme.database_utils.entities;

import java.util.List;

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
}
