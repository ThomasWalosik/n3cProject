package com.example.n3cproject.ui.ordonnance;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OrdonnanceViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OrdonnanceViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Bienvenue sur la page de l'ordonnance");
    }

    public LiveData<String> getText() {
        return mText;
    }
}