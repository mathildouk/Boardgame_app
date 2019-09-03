package com.example.boardgame.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.boardgame.dao.IjeuJoueDAO;
import com.example.boardgame.metier.JeuJoue;
import com.example.boardgame.metier.JeuPossede;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class JeuJoueDAOSQLite implements IjeuJoueDAO {

    Context context;
    Dao<JeuJoue, Integer> jeuDao;
    MyOpenHelper base;

    public JeuJoueDAOSQLite(Context context) {
        this.context=context;
        base=new MyOpenHelper(context);
        SQLiteDatabase db= base.getWritableDatabase();
        try {
            jeuDao = DaoManager.createDao(new AndroidConnectionSource(db), JeuJoue.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<JeuJoue, Integer> getJeuDao() {
        return jeuDao;
    }

    @Override
    public void sauvegarderJeux(List<JeuJoue> jeux) {
        try{
            jeuDao.delete(jeux);
            for(JeuJoue jeu :jeux){
                ajouterJeu(jeu);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<JeuJoue> chargerJeux() {
        try{
            return jeuDao.queryForAll();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ajouterJeu(JeuJoue jeu) {
        try{
            jeuDao.create(jeu);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public JeuJoue getJeubyId(Integer id) {
        try {
            List<JeuJoue> jeux = jeuDao.queryBuilder().where().eq("jeu_id", id).query();
            if (jeux.size()!=0){
                return jeux.get(0);
            }else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public  void deleteJeubyId(Integer id){
        try{ DeleteBuilder<JeuJoue, Integer> deleteBuilder = jeuDao.deleteBuilder();
            deleteBuilder.where().eq("jeu_id",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer nbJeux(){
        try{
            List<JeuJoue> liste =  jeuDao.queryForAll();
            return liste.size();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }


}
