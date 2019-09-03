package com.example.boardgame.dao;

import android.content.Context;

import com.example.boardgame.dao.sqlite.JeuDAOSQLite;

public abstract class JeuDAOfactory {
    public static IjeuDAO getJeuDAO(Context context) {
        return new JeuDAOSQLite(context);
    }
}
