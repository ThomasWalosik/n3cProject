package com.example.n3cproject.bdd;

public class Serveur {
    // classe pour les données à gérer. Cette classe est simple puisque elle est définie par les attributs nomRappel, dateRappel et heureRappel. On ajoute le constructeur ainsi que les accesseurs (getter et setter) :
    private int idRappel;
    private String nomRappel;
    //Add Date dateRappel et Hour heureRappel

    public Serveur(String s, String toString, int i){

    }

    public Serveur(){
        this.nomRappel = nomRappel;
        //TODO
    }

    public void setIdRappel(int idRappel){
        this.idRappel = idRappel;
    }
    public int getIdRappel(){
        return this.idRappel;
    }

    public String getNomRappel(){
        return nomRappel;
    }

    public void setNomRappel(String nomRappel){
        this.nomRappel = nomRappel;
    }

    public String toStringNom(){
        return nomRappel;
    }
    public String toStringDateHeure(){
        return "Le " + "dateRappel" + "à" + "heureRappel";
    }
}
