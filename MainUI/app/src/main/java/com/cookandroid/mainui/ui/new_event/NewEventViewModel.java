package com.cookandroid.mainui.ui.new_event;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewEventViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewEventViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is 이벤트 등록 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}