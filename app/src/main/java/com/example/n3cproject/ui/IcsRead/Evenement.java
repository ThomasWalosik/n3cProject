package com.example.n3cproject.ui.IcsRead;

import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Evenement implements Comparable<Evenement>{
    private final Date dateDebut; // DTSTART
    private final Date dateFin; //DTEND
    private final String lieu; //LOCATION
    private final String uid; //UID
    private final Date dateModif; //DTSTAMP
    private final String sommaire; //SUMMARY
    private final String description; //DESCRIPTION
    private final int sequence; //SEQUENCE
    private EventType type;


    private float timeStart;
    private float timeEnd;
    private float duration;
    private String stringTimeStart = "";
    private String stringTimeEnd = "";

    private String nomUE;
    private String nomGroupe;
    private String stringListeSalles = "";

    // TP; COURS; COURS/TD; cm; EXAMEN; REUNION / RENCONTRE; CONTROLE TERMINAL; SOUTENANCE

    /**
     * Constructeur
     * @param evenement Chaine de caractères contenant uniquement un événement.
     */
    public Evenement(String evenement) {
        this.dateDebut = stringToDate(findPart(evenement, "DTSTART:"));
        this.dateFin = stringToDate(findPart(evenement, "DTEND:"));
        this.lieu = findPart(evenement, "LOCATION:");
        this.uid = findPart(evenement, "UID:");
        this.dateModif = stringToDate(findPart(evenement, "DTSTAMP:"));
        this.sommaire = findPart(evenement, "SUMMARY:");
        this.description = findPart(evenement, "DESCRIPTION:");
        this.sequence = Integer.parseInt(findPart(evenement.replace("END:VEVENT", ""), "SEQUENCE:"));
        determineType(sommaire);
        setTimes();
        traitSummary();
    }


    /**
     * Détermine le type d'événement
     * @param sommaire Sommaire récupéré
     */
    private void determineType(String sommaire) {
        if(sommaire.toUpperCase().startsWith("TP"))
            type = EventType.TP;
        else if(sommaire.toUpperCase().startsWith("COURS"))
            type = EventType.COURS;
        else if(sommaire.toUpperCase().startsWith("COURS/TD"))
            type = EventType.COURS_TD;
        else if(sommaire.toUpperCase().startsWith("EXAMEN"))
            type = EventType.EXAMEN;
        else if(sommaire.toUpperCase().startsWith("REUNION / RENCONTRE"))
            type = EventType.REUNION_RENCONTRE;
        else if(sommaire.toUpperCase().startsWith("CONTROLE TERMINAL"))
            type = EventType.CONTROLE_TERMINAL;
        else if(sommaire.toUpperCase().startsWith("SOUTENANCE"))
            type = EventType.SOUTENANCE;
        else if(sommaire.toUpperCase().startsWith("CM"))
            type = EventType.CM;
        else if (sommaire.toUpperCase().startsWith("CONTROLE PARTIEL"))
            type = EventType.CONTROLE_PARTIEL;
        else if (sommaire.toUpperCase().startsWith("CONTROLE CONTINU"))
            type = EventType.CONTROLE_CONTINU;
        else if (sommaire.toUpperCase().startsWith("CONSULTATION DE COPIES"))
            type = EventType.CONSULTATION_DE_COPIES;
        else if (sommaire.toUpperCase().startsWith("TP NON ENCADR"))
            type = EventType.TP_NON_ENCADRE;
        else
            type = EventType.UNDETERMINED;

        /* A rajouter ?
        Controle partiel
        Controle Continu
        Consultation de copies
        TP non encadré
         */
    }

    /**
     * Retourne les informations contenues après le tofind dans l'evenenement
     * @param evenement Chaine de caractère dans laquelle chercher
     * @param toFind Ce qui est à chercher
     * @return Le texte intéressant
     */
    private String findPart(String evenement, String toFind) {
        int indexFirst = evenement.indexOf(toFind) + toFind.length();
        String ret = evenement.substring(indexFirst);
        int indexLast = ret.indexOf("\n\r");
        if (indexLast > -1) ret = ret.substring(0,indexLast);
        return ret;
    }

    /**
     * Retourne la date à partir de la date de l'ics
     * @param originalDate date de l'ics
     * @return la date si ez, null sinon
     */
    private static Date stringToDate(String originalDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.FRANCE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            return sdf.parse(originalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private void setTimes(){
        
		int heureDebut = getDateDebut().getHours();
        int minuteDebut = getDateDebut().getMinutes();
        int heureFin = getDateFin().getHours();
        int minuteFin = getDateFin().getMinutes();

        timeStart = heureDebut + ((float) minuteDebut)/60;
        timeEnd = heureFin + ((float) minuteFin)/60;
        //System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTT  " + timeStart + "    " + timeEnd);
        duration = timeEnd - timeStart;

        if (heureDebut<10) stringTimeStart += "0";
        stringTimeStart += heureDebut + "h";
        if (minuteDebut<10) stringTimeStart += "0";
        stringTimeStart += minuteDebut;

        if (heureFin<10) stringTimeEnd += "0";
        stringTimeEnd += heureFin + "h";
        if (minuteFin<10) stringTimeEnd += "0";
        stringTimeEnd += minuteFin;
    }

    private void traitSummary(){
        String summary = getSommaire();
        String[] tabSummary = summary.split(";");
        String elementCourant;
        //categorie := tabSummary[0]
        nomUE = tabSummary[1].substring(0,tabSummary[1].length()-1);
        nomGroupe = tabSummary[2].substring(0,tabSummary[2].length()-1); //TODO Attention, il se peut qu'il y ait plusieurs groupes, jsp
        if (tabSummary.length > 3){
            elementCourant = tabSummary[3];
            stringListeSalles += elementCourant.substring(0,elementCourant.length()-1);
            for (int i=4; i<tabSummary.length; i++){
                elementCourant = tabSummary[i];
                stringListeSalles += ", " + elementCourant.substring(0,elementCourant.length()-1);
            }
        }else{
            stringListeSalles = "Salle inconnue";
        }

    }

    public int getBgColor(){
        int bgColor = Color.parseColor("#"+"8c8c8d");
        if (type == EventType.CM) bgColor = Color.parseColor("#"+"4c4cd9");
        if (type == EventType.TP) bgColor = Color.parseColor("#" + "023701");
        if (type == EventType.COURS) bgColor = Color.parseColor("#"+"4c4cd9");
        if (type == EventType.COURS_TD) bgColor = Color.parseColor("#"+"32111d");
        if (type == EventType.EXAMEN) bgColor = Color.parseColor("#"+"a04418");
        if (type == EventType.REUNION_RENCONTRE) bgColor = Color.parseColor("#"+"a09318");
        if (type == EventType.CONTROLE_TERMINAL) bgColor = Color.parseColor("#"+"f34518");
        if (type == EventType.SOUTENANCE) bgColor = Color.parseColor("#"+"f34592");
        if (type == EventType.UNDETERMINED) bgColor = Color.parseColor("#"+"8c8c8d");
        //TODO Rajouter les nouvelles couleurs des nouveaux enums
        return bgColor;
    }

    public int getTextColor(){
        //TODO
        return Color.parseColor("#" + "000000");
    }

    @Override
    public String toString() {
        return "EvenementPasICS [dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", lieu=" + lieu + ", uid=" + uid
                + ", dateModif=" + dateModif + ", sommaire=" + sommaire + ", description=" + description + ", sequence="
                + sequence + ", type=" + type + "]";
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public Date getDateModif() {
        return dateModif;
    }

    public String getSommaire() {
        return sommaire;
    }

    public String getUID(){return uid;}

    public float getTimeStart(){return timeStart;}

    public float getTimeEnd(){return timeEnd;}

    public float getDuration(){return duration;}

    public String getStringTimeStart(){return stringTimeStart;}

    public String getStringTimeEnd(){return stringTimeEnd;}

    public String getDescription(){return description;}

    public String getNomUE(){return nomUE;}

    public String getNomGroupe(){return nomGroupe;}

    public String getStringListeSalles(){return stringListeSalles;}

    public EventType getType(){return type;}

    public int compareTo(Evenement evenementToCompare){
        return uid.compareTo(evenementToCompare.getUID());
    }
}
