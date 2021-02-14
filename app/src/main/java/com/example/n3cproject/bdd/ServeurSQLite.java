package com.example.n3cproject.bdd;
// Pour créer et mettre à jour une base de données dans une application Android, on doit créer une classe qui hérite de SQLiteOpenHelper.

import android.content.Context;
import android.database.sqlite.*;

public class ServeurSQLite extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "serveurs.db";
    public static final int    DATABASE_VERSION = 1;
    public static final String TABLE_SERVEURS = "table_serveurs";
    public static final String COL_ID = "ID";
    public static final String COL_NOM = "NOM";
    public static final String COL_ADRESSE_IP = "ADRESSE_IP";
    public static final String COL_PORT = "PORT";
    //TODO
    public static final int    NUM_COL_ID = 0;
    public static final int    NUM_COL_NOM = 1;
    public static final int    NUM_COL_ADRESSE_IP = 2;
    public static final int    NUM_COL_PORT = 3;

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_SERVEURS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NOM + " TEXT NOT NULL, "
            + COL_ADRESSE_IP + " TEXT NOT NULL,"
            + COL_PORT + " INTEGER NOT NULL);";

    // Dans le constructeur de cette sous-classe, on appelera la méthode super() de SQLiteOpenHelper, en précisant le nom de la base de données et sa version actuelle
    public ServeurSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Pour accéder à une base de données qui n’est pas encore créée
    // l'attribut SQLiteDatabase qui est la représentation Java de la base de données.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    // Si la version de la base de données évolue, cette méthode permettra de mettre à jour le schéma de base de données existant ou de supprimer la base de données existante et la recréer par la méthode onCreate()
    // l'attribut SQLiteDatabase qui est la représentation Java de la base de données.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // au choix : on supprime la table puis on la recrée
        db.execSQL("DROP TABLE " + TABLE_SERVEURS + ";");
        onCreate(db);
    }
}