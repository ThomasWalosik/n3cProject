package com.example.n3cproject.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ServeurBDD
{
    private SQLiteDatabase bdd = null;
    private ServeurSQLite serveurSQLite = null;

    public ServeurBDD(Context context)
    {
        // cn crée la BDD et sa table
        serveurSQLite = new ServeurSQLite(context);
    }

    public void open()
    {
        // on ouvre la BDD en écriture
        if (bdd == null)
            bdd = serveurSQLite.getWritableDatabase();
    }

    public void close()
    {
        if (bdd != null)
            if (bdd.isOpen())
                bdd.close();
    }

    public SQLiteDatabase getBDD()
    {
        return bdd;
    }

    public long insererServeur(Serveur serveur)
    {
        ContentValues values = new ContentValues();

        values.put(ServeurSQLite.COL_NOM, serveur.getNomRappel());
        //values.put(ServeurSQLite.COL_ADRESSE_IP, serveur.getAdresseIP());
        //values.put(ServeurSQLite.COL_PORT, serveur.getPort());

        return bdd.insert(ServeurSQLite.TABLE_SERVEURS, null, values);
    }

    public int modifierServeur(int id, Serveur serveur)
    {
        ContentValues values = new ContentValues();
        values.put(ServeurSQLite.COL_NOM, serveur.getNomRappel());
        //values.put(ServeurSQLite.COL_ADRESSE_IP, serveur.getAdresseIP());
        //values.put(ServeurSQLite.COL_PORT, serveur.getPort());

        return bdd.update(ServeurSQLite.TABLE_SERVEURS, values, ServeurSQLite.COL_ID + " = " + id, null);
    }

    public int supprimerServeur(int id)
    {
        return bdd.delete(ServeurSQLite.TABLE_SERVEURS, ServeurSQLite.COL_ID + " = " + id, null);
    }

    public Serveur getServeur(String nom)
    {
        Cursor c = bdd.query(ServeurSQLite.TABLE_SERVEURS, new String[] {ServeurSQLite.COL_ID, ServeurSQLite.COL_NOM, ServeurSQLite.COL_ADRESSE_IP, ServeurSQLite.COL_PORT}, ServeurSQLite.COL_NOM + " LIKE \"" + nom +"\"", null, null, null, null);

        return cursorToServeur(c, true);
    }

    public Serveur getServeurByAdresseIP(String adresseIP) {
        // rawQuery execute une instruction SQL
        Cursor c = bdd.rawQuery(String.format("SELECT * FROM%s WHERE %s = ?", ServeurSQLite.TABLE_SERVEURS, ServeurSQLite.COL_ADRESSE_IP), new String[] { adresseIP });
        //Cursor c = bdd.rawQuery("SELECT * FROM" + ServeurSQLite.TABLE_SERVEURS + " WHERE " + ServeurSQLite.COL_ADRESSE_IP + " = ?", new String[] { adresseIP });

        return cursorToServeur(c, true);
    }

    public List<Serveur> getServeurs()
    {
        List<Serveur> serveurs = new ArrayList<Serveur>();
        // query() fournit une interface structurée pour une reqête SQL
        Cursor cursor = bdd.query(ServeurSQLite.TABLE_SERVEURS, new String[] {ServeurSQLite.COL_ID, ServeurSQLite.COL_NOM, ServeurSQLite.COL_ADRESSE_IP, ServeurSQLite.COL_PORT}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Serveur serveur = cursorToServeur(cursor, false);
            serveurs.add(serveur);
            cursor.moveToNext();
        }

        cursor.close();

        return serveurs;
    }

    // Cette méthode permet de convertir un cursor en un objet de type Serveur
    private Serveur cursorToServeur(Cursor c, boolean one)
    {
        if (c.getCount() == 0)
            return null;

        if(one == true)
            c.moveToFirst();

        Serveur serveur = new Serveur();

        serveur.setIdRappel(c.getInt(ServeurSQLite.NUM_COL_ID));
        serveur.setNomRappel(c.getString(ServeurSQLite.NUM_COL_NOM));
        //serveur.setAdresseIP(c.getString(ServeurSQLite.NUM_COL_ADRESSE_IP));
        //serveur.setPort(c.getInt(ServeurSQLite.NUM_COL_PORT));

        if(one == true)
            c.close();

        return serveur;
    }
}
