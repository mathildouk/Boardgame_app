package com.example.boardgame.dao.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.boardgame.dao.IjeuDAO;
import com.example.boardgame.metier.Jeu;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JeuDAOSQLite implements IjeuDAO {
    Context context;
    Dao<Jeu, Integer> jeuDao;
    MyOpenHelper base;

    public JeuDAOSQLite(Context context) {
        this.context = context;
        base = new MyOpenHelper(context);
        SQLiteDatabase db = base.getWritableDatabase();
        try {
            jeuDao = DaoManager.createDao(new AndroidConnectionSource(db), Jeu.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Dao<Jeu, Integer> getJeuDao() {
        return jeuDao;
    }

    @Override
    public void sauvegarderJeux(List<Jeu> jeux) {
        try {
            jeuDao.delete(jeux);
            for (Jeu jeu : jeux) {
                ajouterJeu(jeu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Jeu> chargerJeux() {
        try {
            return jeuDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public void ajouterJeu(Jeu jeu) {
        try {
            jeuDao.create(jeu);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Jeu getJeubyId(Integer id) {
        try {
            List<Jeu> jeux = jeuDao.queryBuilder().where().eq("identifiant", id).query();
            return jeux.get(0);
          //  if (jeux.size()!=0){
            //         return jeux.get(0);
            //    }else {
            //       return null;
            //   }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Jeu> getJeuxbyName(String nomJeu){
        try {
            List<Jeu> jeux = jeuDao.queryBuilder().where().like("nom", "%"+nomJeu+"%").query();
            return jeux;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public  void deleteJeubyId(Integer id){
        try{ DeleteBuilder<Jeu, Integer> deleteBuilder = jeuDao.deleteBuilder();
            deleteBuilder.where().eq("identifiant",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
