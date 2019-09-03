package com.example.boardgame.dao;

import android.content.Context;

import com.example.boardgame.dao.sqlite.JeuPossedeDAOSQLite;

public class JeuPossedeDAOfactory {
    public static IJeuPossedeDAO getJeuPossedeDAO(Context context) {return new JeuPossedeDAOSQLite(context);}
}
