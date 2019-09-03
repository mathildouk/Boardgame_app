package com.example.boardgame.dao.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.boardgame.dao.IJeuPossedeDAO;
import com.example.boardgame.metier.JeuPossede;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class JeuPossedeDAOSQLite implements IJeuPossedeDAO {

    Context context;
    Dao<JeuPossede, Integer> jeuDao;
    MyOpenHelper base;

    public JeuPossedeDAOSQLite(Context context) {
        this.context=context;
        base=new MyOpenHelper(context);
        SQLiteDatabase db= base.getWritableDatabase();
        try {
            jeuDao = DaoManager.createDao(new AndroidConnectionSource(db), JeuPossede.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<JeuPossede, Integer> getJeuDao() {
        return jeuDao;
    }

    @Override
    public void sauvegarderJeux(List<JeuPossede> jeux) {
        try{
            jeuDao.delete(jeux);
            for(JeuPossede jeu :jeux){
                ajouterJeu(jeu);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<JeuPossede> chargerJeux() {
        try{
            return jeuDao.queryForAll();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ajouterJeu(JeuPossede jeu) {
        try{
            jeuDao.create(jeu);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public JeuPossede getJeubyId(Integer id) {   try {
        List<JeuPossede> jeux = jeuDao.queryBuilder().where().eq("jeu_id", id).query();
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
        try{ DeleteBuilder<JeuPossede, Integer> deleteBuilder = jeuDao.deleteBuilder();
            deleteBuilder.where().eq("jeu_id",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Integer nbJeux(){
        try{
            List<JeuPossede> liste =  jeuDao.queryForAll();
            return liste.size();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
