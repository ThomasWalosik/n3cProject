package com.example.n3cproject.ui.IcsRead;

import java.util.ArrayList;
import java.util.List;

class CompletionList {
    // Désolé pour le magnifique nom, mais c'est la faute au Json
    private final List<CompletionEntry> d = new ArrayList<>();

    List<CompletionEntry> getCompletionList() {
        return d;
    }

    @Override
    public String toString() {
        return "CompletionList [ce=" + d + "]";
    }

}

