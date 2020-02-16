package com.hkr.quizme.ui.myQuizzes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyQuizzesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyQuizzesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}