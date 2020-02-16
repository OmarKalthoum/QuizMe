package com.hkr.quizme.ui.takeQuiz;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ParentCreator {
    static ParentCreator parentCreator;
    List<Parent> parents;

    public ParentCreator(Context context) {
        parents = new ArrayList<>();
        // TODO : GET QUIZ-CATEGORIES FROM DATABASE AND ADD THEM TO THE LIST BELOW
        for (int i = 1; i < 10; i++) {
            Parent item = new Parent("Algorithm and Data Structure " + (i + 1));
            parents.add(item);
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
