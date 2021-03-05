package com.example.n3cproject.ui.recherche;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RechercheViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public RechercheViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Bienvenue sur l'espace méditation");
    }

    public LiveData<String> getText() {
        return mText;
    }
}