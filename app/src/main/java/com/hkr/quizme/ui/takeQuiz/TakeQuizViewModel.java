package com.hkr.quizme.ui.takeQuiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TakeQuizViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TakeQuizViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}