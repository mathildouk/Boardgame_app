package com.example.boardgame.dao;

import android.content.Context;

import com.example.boardgame.dao.sqlite.JeuJoueDAOSQLite;

public class JeuJoueDAOfactory {
    public static IjeuJoueDAO getJeuJoueDAO(Context context) {return new JeuJoueDAOSQLite(context);}
}
