package com.example.n3cproject.ui.IcsRead;

import com.example.n3cproject.BuildConfig;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ICSTrait {

    /**
     * Unfolding de l'ics (mise en forme des champs multilignes en une seule ligne)
     * @param originalICS Chaine de caractères contenant l'ICS foldé
     * @return l'ics, mais unfoldé
     */
    public String unfoldingICS(String originalICS) {
        Reader unfoldedReader = new StringReader(originalICS);
        BufferedReader br = new BufferedReader(unfoldedReader);
        String texte = "";
        String line;
        int currentICSObject = 0;
        // On enregiste les noms des ICSObject dans une liste
        ArrayList<String> icsnames = new ArrayList<>();
        for(ICSObject ob : ICSObject.values())
            icsnames.add(ob.name());
        try {
            while((line = br.readLine()) != null) {
                // On regarde si la ligne contient un ICSObject
                boolean found = false;
                for(String s : icsnames) {
                    if(line.startsWith(s))
                        found = true;
                }
                // Si elle contient bien un ICSObject, on écrit dans une nouvelle ligne
                if(found) {
                    texte += "\n\r" + line;
                } else {
                    // Si ce n'est pas le cas, on concatène à la ligne déjà existante
                    if(line.length() > 1)
                        texte += line.substring(1);
                }
                currentICSObject++;
                // Modulo sur le nombre d'ICSObject de l'événement
                if(currentICSObject > (ICSObject.values().length-1)) {
                    currentICSObject = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texte;
    }

    /**
     * Supprime les informations inutiles de l'ICS formatté en String
     * @return trimedICS String contenant les différentes activités de l'ICS
     */
    private String icsTriming(String untrimedICS) {
        String trimedICS = untrimedICS;
        trimedICS = trimedICS.replaceFirst("(?s)BEGIN:VCALENDAR(.*)X-PUBLISHED-TTL:(.{5})BEGIN:VEVENT", "BEGIN:VEVENT");
        trimedICS = trimedICS.replaceFirst("END:VCALENDAR", "");
        return trimedICS;
    }

    /**
     * Transformation d'un ICS en tableau d'événements
     * @param filename nom du fichier ics
     * @return ArrayList d'événements
     */
    public ArrayList<Evenement> getEvents(String filename) {
        ArrayList<Evenement> events = new ArrayList<>();
        String foldedICS = readICS(filename);
        // Suppression des informations inutiles
        foldedICS = icsTriming(foldedICS);
        // Unfolding de l'ICS
        String unfoldedICS = unfoldingICS(foldedICS);

        // Lecture de l'unfoldedICS et séparation des événements en plusieurs String
        ArrayList<String> tmp = new ArrayList<>();
        while(unfoldedICS.endsWith("END:VEVENT")) {
            String tmptmp = findEvent(unfoldedICS, "BEGIN:VEVENT");
            unfoldedICS = unfoldedICS.replace(tmptmp, "");
            tmptmp = tmptmp.replaceAll("BEGIN:VEVENT", "");
            tmptmp = tmptmp.replaceAll("END:VEVENT", "");
            tmp.add(tmptmp);
            System.out.println(tmptmp);
        }

        // Création des différents événements
        for(String s : tmp){
            events.add(new Evenement(s));
        }
        return events;
    }

    /**
     * Savegarde d'un tableau d'événements en JSON
     * @param filename Nom du fichier
     * @param events Evenements
     */
    public void saveToJson(String filename,ArrayList<Evenement> events){
        // Transformation de la liste en json
        File file=new File(filename);
        //String toSave = new Gson().toJson(events);
        try {
            // Ecriture du json
            //  FileOutputStream output = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            FileOutputStream output= new FileOutputStream(file);
            //output.write(toSave.getBytes());
            output.close();
        } catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        } catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    /**
     * Lecture d'un fichier JSon
     * @param filename nom du fichier
     * @return Liste d'événements
     */
    public ArrayList<Evenement> readFromJson(String filename){
        ArrayList<Evenement> events = new ArrayList<>();
        File file= new File(filename);
        try{
            //Gson gson = new Gson();
            FileInputStream input =new FileInputStream(file);
            String content = "";
            int data;
            while((data = input.read()) > 0){
                content += (char)data;
            }
            input.close();
            // Récupération du type d'ArrayList<EvenementPasICS>
            //Type listType = new TypeToken<ArrayList<Evenement>>(){}.getType();
            // Lecture du Json
            //events = gson.fromJson(content, listType);
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return events;
    }

    /**
     * Trouve le prochain événement d'une chaine de caractères
     * @param ics Contenu du fichiers ICS
     * @param toFind généralement BEGIN:VEVENT pour le début d'un événement
     * @return Retourne l'événement suivant
     */
    private static String findEvent(String ics, String toFind) {
        int indexFirst = ics.indexOf(toFind);
        String ret = ics.substring(indexFirst);
        int indexLast = ret.indexOf("END:VEVENT");
        if (indexLast > -1) ret = ret.substring(0,indexLast + "END:VEVENT".length());
        return ret;
    }

    /**
     * Lecture de l'ICS depuis le stockage du téléphone
     * @return Le String contenant l'ICS lu
     */
    public String readICS(String filename){
        File file= new File(filename);
        String ret = "";
        try{
            FileInputStream fileInputStream = new FileInputStream("/isOp.txt/isOp.txt/"+ BuildConfig.APPLICATION_ID+"/ics/");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while((line = bufferedReader.readLine()) != null){
                ret+=line + "\n";
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
        }catch(IOException ex){
            System.err.println("Erreur de lecture de l'ICS");
            ex.printStackTrace();
        }
        return ret;
    }
}
