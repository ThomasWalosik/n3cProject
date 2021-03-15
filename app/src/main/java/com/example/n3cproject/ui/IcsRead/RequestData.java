package com.example.n3cproject.ui.IcsRead;

import android.os.AsyncTask;

public class RequestData extends AsyncTask<Void, Void, String> {
    private final String eventTarget;
    private final String eventArgument;
    private final String viewState;
    private final String viewStateGenerator;
    private final String eventValidation;

    /**
     * Constructeur de base nécessaire à une reqiête
     * @param eventTarget Nécessaire à l'ASP.Net
     * @param eventArgument Nécessaire à l'ASP.Net
     * @param viewState Nécessaire à l'ASP.Net
     * @param viewStateGenerator Nécessaire à l'ASP.Net
     * @param eventValidation Nécessaire à l'ASP.Net
     */
    public RequestData(String eventTarget, String eventArgument, String viewState, String viewStateGenerator,
                       String eventValidation) {
        this.eventTarget = eventTarget;
        this.eventArgument = eventArgument;
        this.viewState = viewState;
        this.viewStateGenerator = viewStateGenerator;
        this.eventValidation = eventValidation;
    }

    /**
     * Création d'un RequestData en fonction d'une réponse de requête
     * @param input réponse de requête précédente
     */
    RequestData(String input){
        this.viewState = findViewState(input);
        this.viewStateGenerator = findViewStateGenerator(input);
        this.eventTarget = findEventTarget(input);
        this.eventArgument = findEventArgument(input);
        this.eventValidation = findEventValidation(input);
    }

    // TODO modifier le code redondant
    private String findViewState(String input) {
        int indexFirst = input.indexOf("|__VIEWSTATE|") + "|__VIEWSTATE|".length();
        String viewstate = input.substring(indexFirst);
        int indexLast = viewstate.indexOf("|");
        if (indexLast > -1) viewstate = viewstate.substring(0,indexLast);
        return viewstate;
    }

    private String findViewStateGenerator(String input) {
        int indexFirst = input.indexOf("|__VIEWSTATEGENERATOR|") + "|__VIEWSTATEGENERATOR|".length();
        String viewStateGenerator = input.substring(indexFirst);
        int indexLast = viewStateGenerator.indexOf("|");
        if (indexLast > -1) viewStateGenerator = viewStateGenerator.substring(0,indexLast);
        return viewStateGenerator;
    }

    private String findEventTarget(String input) {
        int indexFirst = input.indexOf("|__EVENTTARGET|") + "|__EVENTTARGET|".length();
        String eventTarget = input.substring(indexFirst);
        int indexLast = eventTarget.indexOf("|");
        if (indexLast > -1) eventTarget = eventTarget.substring(0,indexLast);
        return eventTarget;
    }

    private String findEventArgument(String input) {
        int indexFirst = input.indexOf("|__EVENTARGUMENT|") + "|__EVENTARGUMENT|".length();
        String eventArgument = input.substring(indexFirst);
        int indexLast = eventArgument.indexOf("|");
        if (indexLast > -1) eventArgument = eventArgument.substring(0,indexLast);
        return eventArgument;
    }

    private String findEventValidation(String input) {
        int indexFirst = input.indexOf("|__EVENTVALIDATION|") + "|__EVENTVALIDATION|".length();
        String eventValidation = input.substring(indexFirst);
        int indexLast = eventValidation.indexOf("|");
        if (indexLast > -1) eventValidation = eventValidation.substring(0,indexLast);
        return eventValidation;
    }

    String getEventTarget() {
        return eventTarget;
    }

    String getEventArgument() {
        return eventArgument;
    }

    String getViewState() {
        return viewState;
    }

    String getViewStateGenerator() {
        return viewStateGenerator;
    }

    String getEventValidation() {
        return eventValidation;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}

