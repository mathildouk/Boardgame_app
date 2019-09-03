package com.example.boardgame.dao;

import android.content.Context;

import com.example.boardgame.dao.sqlite.JeuAJouerDAOSQLite;

public class JeuAJouerDAOfactory {
    public static IjeuAJouerDAO getJeuAJouerDAO(Context context) {return new JeuAJouerDAOSQLite(context);}
}
