package com.example.boardgame.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.boardgame.dao.IJeuAAcheterDAO;
import com.example.boardgame.metier.JeuAAcheter;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class JeuAAcheterDAOSQLite implements IJeuAAcheterDAO {

    Context context;
    Dao<JeuAAcheter, Integer> jeuDao;
    MyOpenHelper base;

    public JeuAAcheterDAOSQLite(Context context) {
        this.context=context;
        base=new MyOpenHelper(context);
        SQLiteDatabase db= base.getWritableDatabase();
        try {
            jeuDao = DaoManager.createDao(new AndroidConnectionSource(db), JeuAAcheter.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<JeuAAcheter, Integer> getJeuDao() {
        return jeuDao;
    }

    @Override
    public void sauvegarderJeux(List<JeuAAcheter> jeux) {
        try{
            jeuDao.delete(jeux);
            for(JeuAAcheter jeu :jeux){
                ajouterJeu(jeu);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<JeuAAcheter> chargerJeux() {
        try{
            return jeuDao.queryForAll();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ajouterJeu(JeuAAcheter jeu) {
        try{
            jeuDao.create(jeu);
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    @Override
    public JeuAAcheter getJeubyId(Integer id) {
        try {
            List<JeuAAcheter> jeux = jeuDao.queryBuilder().where().eq("jeu_id", id).query();
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
        try{ DeleteBuilder<JeuAAcheter, Integer> deleteBuilder = jeuDao.deleteBuilder();
            deleteBuilder.where().eq("jeu_id",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer nbJeux(){
        try{
            List<JeuAAcheter> liste =  jeuDao.queryForAll();
            return liste.size();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
