package com.example.boardgame.dao;

import android.content.Context;

import com.example.boardgame.dao.sqlite.PartieDAOSQLite;

public abstract class PartieDAOfactory {
    public static IpartieDAO getPartieDAO(Context context) {
        return new PartieDAOSQLite(context);
    }


}