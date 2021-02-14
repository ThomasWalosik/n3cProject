package com.example.n3cproject.ui.meditation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MeditationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MeditationViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Bienvenue sur l'espace méditation");
    }

    public LiveData<String> getText() {
        return mText;
    }
}