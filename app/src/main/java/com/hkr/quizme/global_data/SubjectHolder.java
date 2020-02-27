package com.hkr.quizme.global_data;

import com.hkr.quizme.database_utils.entities.Subject;

public class SubjectHolder {
    private static SubjectHolder instance;
    private Subject subject;

    public static SubjectHolder getInstance() {
        if (instance == null)
            instance = new SubjectHolder();
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
