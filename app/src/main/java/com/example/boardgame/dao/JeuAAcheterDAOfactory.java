package com.example.boardgame.dao;

import android.content.Context;

import com.example.boardgame.dao.sqlite.JeuAAcheterDAOSQLite;

public class JeuAAcheterDAOfactory {
    public static IJeuAAcheterDAO getJeuAAcheterDAO(Context context) {return new JeuAAcheterDAOSQLite(context);}
}
