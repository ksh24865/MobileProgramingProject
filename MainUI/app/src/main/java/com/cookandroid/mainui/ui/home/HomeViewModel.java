package com.cookandroid.mainui.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("현재 위치 : 동작구"); // 현재 위치 넣어야 함.
    }

    public LiveData<String> getText() {
        return mText;
    }
}