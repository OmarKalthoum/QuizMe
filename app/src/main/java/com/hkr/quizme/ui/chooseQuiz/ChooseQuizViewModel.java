package com.hkr.quizme.ui.chooseQuiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChooseQuizViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ChooseQuizViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fuck fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}