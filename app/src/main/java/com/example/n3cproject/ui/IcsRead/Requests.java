package com.example.n3cproject.ui.IcsRead;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

class Requests {

    private final String USER_AGENT = "Mozilla/5.0";
    private  String cookie = "";
    private final String ICS_URL = "https://edt.univ-tlse3.fr/CalendarFeed/groups.aspx";
    private final String COMPLETIONLIST_URL = "https://edt.univ-tlse3.fr/CalendarFeed/groups.aspx/GetCompletionList";

    /**
     * Effectue une requête et renvoie la réponse reçue
     * @param url Adresse sur laquelle faire la requête
     * @param contentType Type de données à envoyer
     * @param params Paramètres de la requête
     * @return La réponse reçue pour la requête
     * @throws IOException Si la requête échoue
     */
    private String doRequest(String url, String contentType, String params) throws IOException {
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        // Header de requ�te
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "fr-FR,fr;q=0.5");
        if(!cookie.isEmpty())
            con.setRequestProperty("Cookie", "ASP.NET_SessionId=" + cookie);
        // Type d'envoi de donn�es
        con.setRequestProperty("content-type", contentType);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        // Affichage des informations usuelles
        int responseCode = con.getResponseCode();

        // Lecture de la r�ponse
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        // Cr�ation du StringBuffer contenant la réponse
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        updateCookie(con);

        return response.toString();
    }

    /**
     * Met à jour le cookie en fonction d'une connexion �tablie
     * @param con La connexion permettant d'obtenir le cookie
     */
    private void updateCookie(HttpsURLConnection con) {
        // R�cup�ration du ASP.NET_SessionId aka le cookie de session
        String serverCookies = con.getHeaderField("Set-Cookie");
        if (serverCookies != null){
            for(String s : serverCookies.split(";")){
                String[] parts = s.trim().split("=");
                if( parts.length==2 && "ASP.NET_SessionId".equals(parts[0])){
                    cookie = parts[1]; // La variable session est r�utilis�e pour les requ�tes suivantes
                    break;
                }
            }
        }
    }

    /**
     * Fonction de récupération des groupes possibles pour une recherche
     * @param toSearch Chaine de caractère à chercher
     * @throws IOException Si la requète échoue
     * @return
     */
    CompletionList getCompletionList(String toSearch) throws IOException {
        // Pr�fixe = chaine � chercher
        // count = nombre de r�sultats (� ne pas trop toucher)
        String params = "{\"prefixText\":\"" + toSearch + "\",\"count\":20}";

        String response = doRequest(COMPLETIONLIST_URL, "application/json", params);

        // Mise en forme du String de fa�on � ce qu'il devienne un JSon l�gal
        response = response.replace("\\", "");
        response = response.replace("\"{", "{");
        response = response.replace("}\"", "}");

        // Transformation du JSon en objet
        //Gson gson = new Gson();
        //return gson.fromJson(response, CompletionList.class);
        return null;
    }

    /**
     * Fonction renvoyant le premier résultat contenant le String
     * @param cl Liste d'autocompletion proposée par le site
     * @param toSearch Chaine à chercher
     * @return Chaine contenant la valeur du groupe
     */
    CompletionEntry getFirstMatchingEntry(CompletionList cl, String toSearch) {
        int cpt = 0;
        boolean found = false;
        CompletionEntry ce = null;
        while(cpt != cl.getCompletionList().size() && !found) {
            ce = cl.getCompletionList().get(cpt);
            if(ce.getFirst().toUpperCase().contains(toSearch.toUpperCase())) {
                found = true;
            }
            cpt++;
        }
        if(found)
            return ce;
        return null;
    }

    /**
     * R�cup�re le lien des ICS à partir d'une ligne quasi html (je dis quasi html car c'est un String bizarre pas html puisqu'on peut pas utiliser JSOUP :/
     * @param input Chaine de caractère du retour de requète de récupération du lien d'ICS
     * @return Le lien tant recherché
     */
    private String getUrl(String input) {
        int indexFirst = input.indexOf("<input name=\"ctl00$ColumnLeft$FEED_URL_TB_ID\"") + " <input name=\"ctl00$ColumnLeft$FEED_URL_TB_ID\"".length();
        input = input.substring(indexFirst);
        indexFirst = input.indexOf("value=")  + "value=".length();
        int indexLast = input.indexOf("schedule.ics\"") + "schedule.ics".length();
        return input.substring(indexFirst+1,indexLast);
    }

    /**
     * Ajout du groupe à la liste des groupes voulus
     * @param ce Groupe à ajouter à la sélection
     * @param brd Information de requète
     * @return La réponse de la requète
     * @throws IOException Erreur de requète
     */
    String addGroup(CompletionEntry ce, RequestData brd) throws IOException {
        // Pr�paration des param�tres pour la requ�te
        String params = "ctl00%24ScriptManager1=ctl00%24ColumnLeft%24ctl00%7Cctl00%24ColumnLeft%24ADD_BTN_ID"
                + "&__EVENTTARGET=" + URLEncoder.encode(brd.getEventTarget(), "UTF-8")
                + "&__EVENTARGUMENT=" + URLEncoder.encode(brd.getEventArgument(), "UTF-8")
                + "&__VIEWSTATE=" + URLEncoder.encode(brd.getViewState(), "UTF-8")
                + "&__VIEWSTATEGENERATOR=" + URLEncoder.encode(brd.getViewStateGenerator(), "UTF-8")
                + "&__EVENTVALIDATION=" + URLEncoder.encode(brd.getEventValidation(), "UTF-8")
                + "&ctl00%24ColumnLeft%24RES_HF_ID=" + ce.getSecond()
                + "&ctl00%24ColumnLeft%24RES_NAME_HF_ID=" + URLEncoder.encode(ce.getFirst(), "UTF-8")
                + "&ctl00%24ColumnLeft%24RES_TB_ID=" + URLEncoder.encode(ce.getFirst(), "UTF-8")
                + "&hiddenInputToUpdateATBuffer_CommonToolkitScripts=1"
                + "&__ASYNCPOST=true"
                + "&ctl00%24ColumnLeft%24ADD_BTN_ID=Ajouter%20Groupe";

        return doRequest(ICS_URL, "application/x-www-form-urlencoded; charset=utf-8", params);
    }

    /**
     * Récupération de l'adresse finale de l'ICS
     * @param rd Informations provenant de l'ajout des groupes
     * @return L'adresse de l'ICS
     * @throws IOException Erreur de requète
     */
    String getICSUrl(RequestData rd) throws IOException {
        // Mise en frome des param�tres
        String params = "ctl00%24ScriptManager1=ctl00%24ColumnLeft%24ctl00%7Cctl00%24ColumnLeft%24GET_BTN_ID"
                + "&ctl00%24ColumnLeft%24RES_HF_ID="
                + "&ctl00%24ColumnLeft%24RES_NAME_HF_ID="
                + "&ctl00%24ColumnLeft%24RES_TB_ID="
                + "&hiddenInputToUpdateATBuffer_CommonToolkitScripts="
                + "&__LASTFOCUS="
                + "&__VIEWSTATE=" + URLEncoder.encode(rd.getViewState(), "UTF-8")
                + "&__VIEWSTATEGENERATOR=" + URLEncoder.encode(rd.getViewStateGenerator(), "UTF-8")
                + "&__EVENTTARGET=" + URLEncoder.encode(rd.getEventTarget(), "UTF-8")
                + "&__EVENTARGUMENT=" + URLEncoder.encode(rd.getEventArgument(), "UTF-8")
                + "&__EVENTVALIDATION=" + URLEncoder.encode(rd.getEventValidation(), "UTF-8")
                + "&__ASYNCPOST=true"
                + "&ctl00%24ColumnLeft%24GET_BTN_ID=Obtenir%20le%20Flux";

        String response = doRequest(ICS_URL, "application/x-www-form-urlencoded; charset=utf-8", params);

        // On retourne l'URL r�cup�r�e
        return getUrl(response);
    }

    /**
     * Retourne les valeurs de la page de base.
     */
    void getDefaultPageValues() {
        /*Document mainPage = null;

        try {
            mainPage = Jsoup.connect(ICS_URL).timeout(2*1000).get();                                        //timeout 3 sec
            String eventTarget = mainPage.selectFirst("input[name=__EVENTTARGET]").attr("value");
            String eventArgument = mainPage.selectFirst("input[name=__EVENTARGUMENT]").attr("value");
            String viewState = mainPage.selectFirst("input[name=__VIEWSTATE]").attr("value");
            String viewStateGenerator = mainPage.selectFirst("input[name=__VIEWSTATEGENERATOR]").attr("value");
            String eventValidation = mainPage.selectFirst("input[name=__EVENTVALIDATION]").attr("value");
            return new RequestData(eventTarget, eventArgument, viewState, viewStateGenerator, eventValidation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
*/
    }

}
