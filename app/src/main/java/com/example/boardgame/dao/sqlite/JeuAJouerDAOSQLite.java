package com.example.boardgame.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.boardgame.dao.IjeuAJouerDAO;
import com.example.boardgame.metier.JeuAJouer;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class JeuAJouerDAOSQLite implements IjeuAJouerDAO {
    Context context;
    Dao<JeuAJouer, Integer> jeuDao;
    MyOpenHelper base;

    public JeuAJouerDAOSQLite(Context context) {
        this.context=context;
        base=new MyOpenHelper(context);
        SQLiteDatabase db= base.getWritableDatabase();
        try {
            jeuDao = DaoManager.createDao(new AndroidConnectionSource(db), JeuAJouer.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<JeuAJouer, Integer> getJeuDao() {
        return jeuDao;
    }

    @Override
    public void sauvegarderJeux(List<JeuAJouer> jeux) {
        try{
            jeuDao.delete(jeux);
            for(JeuAJouer jeu :jeux){
                ajouterJeu(jeu);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<JeuAJouer> chargerJeux() {
        try{
            return jeuDao.queryForAll();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ajouterJeu(JeuAJouer jeu) {
        try{
            jeuDao.create(jeu);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public JeuAJouer getJeubyId(Integer id) {
        try {
            List<JeuAJouer> jeux = jeuDao.queryBuilder().where().eq("jeu_id", id).query();
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
        try{ DeleteBuilder<JeuAJouer, Integer> deleteBuilder = jeuDao.deleteBuilder();
            deleteBuilder.where().eq("jeu_id",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer nbJeux(){
        try{
            List<JeuAJouer> liste =  jeuDao.queryForAll();
            return liste.size();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
