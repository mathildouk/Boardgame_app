package com.example.boardgame.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.JeuAAcheter;
import com.example.boardgame.metier.JeuAJouer;
import com.example.boardgame.metier.JeuJoue;
import com.example.boardgame.metier.JeuPossede;
import com.example.boardgame.metier.Partie;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "jeux27";
    public static final int DBVERSION = 1;

    public MyOpenHelper(Context context) {
        super(context,DBNAME,null,DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            AndroidConnectionSource connection=new AndroidConnectionSource(db);
            TableUtils.createTable(connection, Jeu.class);
            TableUtils.createTable(connection, JeuJoue.class);
            TableUtils.createTable(connection, JeuAJouer.class);
            TableUtils.createTable(connection, JeuAAcheter.class);
            TableUtils.createTable(connection, JeuPossede.class);
            TableUtils.createTable(connection, Partie.class);
    } catch (SQLException e) {
        e.printStackTrace();
    }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Pas de mises à jour de prévu
    }

}
