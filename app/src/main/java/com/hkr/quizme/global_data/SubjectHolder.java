package com.hkr.quizme.global_data;

import com.hkr.quizme.database_utils.entities.Subject;

public class SubjectHolder {
    private final static SubjectHolder instance = new SubjectHolder();
    private Subject subject;

    public static SubjectHolder getInstance() {
        return instance;
    }

    private SubjectHolder() {

    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
