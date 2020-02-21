package com.hkr.quizme.ui.takeQuiz;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.hkr.quizme.database_utils.entities.Subject;

import java.util.List;
import java.util.UUID;

public class Parent implements ParentObject {

    private String name;
    private List<Object> mChildren;
    private UUID _id;

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public Parent(String name)  {
        this.name = name;
        _id = UUID.randomUUID();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public List<Object> getChildObjectList() {
        return mChildren;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildren = list;
    }
}
