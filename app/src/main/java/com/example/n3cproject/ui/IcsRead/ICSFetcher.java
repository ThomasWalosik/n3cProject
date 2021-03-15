package com.example.n3cproject.ui.IcsRead;


import com.example.n3cproject.BuildConfig;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class ICSFetcher {
    public boolean err;
    public ICSFetcher(){
        err = false;
    }



    /**
     * Mise à jour du fichier ics passé en paramètre en suivant les formations choisies
     * @param filename Nom du fichier ICS à mettre à jour
     * @param formations Formations à inclure dans l'ICS
     */
    public void updateICSFile(String filename, String formations){
        RequestData rd = null;
        String groupAddedResponse; // Réponse reçue lors de l'insertion d'un groupe
        String lienICS;

        Requests requests = new Requests();

        try{
            // Récupération des informations de base sur la page
            requests.getDefaultPageValues();

            // On chercher les identifiants corrects pour ajouter la formations
            String toSearch = formations;
            CompletionList cl = requests.getCompletionList(toSearch);               //si pas internet bloque ici

            CompletionEntry group =  requests.getFirstMatchingEntry(cl, toSearch);

            if (group != null){
                // On ajoute le groupe à l'ics
                groupAddedResponse = requests.addGroup(group, rd);
                rd = new RequestData(groupAddedResponse);

                // On récupère le lien de l'ICS
                lienICS = requests.getICSUrl(rd);

                // Téléchargement de l'ICS
                downloadICSfromUrl(lienICS, filename);
            }

        }catch(IOException ex){
            System.err.println("Erreur lors de la récupération de l'adresse de l'ICS");
        }
    }

    /**
     * Téléchargement de l'ICS depuis l'URL et l'enregistre dans le fichier filename
     * @param urlics URL de l'ics
     * @param filename nom du fichier
     */
    private void downloadICSfromUrl(String urlics, String filename) {

        int count;
        File file = new File( filename );

        try {

            URL url = new URL( urlics );
            URLConnection con = url.openConnection();
            con.connect();

            // Téléchargement de l'ics
            InputStream input = new BufferedInputStream( url.openStream(), 8192 );

            // Output stream
            FileOutputStream output = new FileOutputStream( "/isOp.txt/isOp.txt/"+ BuildConfig.APPLICATION_ID+"/ics/" ,false);

            byte[] data = new byte[4096];
            while ((count = input.read( data )) > 0) {
                // Ecriture des données dans le fichier
                output.write( data, 0, count );
            }
            output.close();
            input.close();
        } catch (IOException ex) {
            System.err.println( "Erreur de téléchargement de l'ICS." );
            err = true;
            ex.printStackTrace();
        }
    }


}



