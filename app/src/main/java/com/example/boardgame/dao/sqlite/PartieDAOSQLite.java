package com.example.boardgame.dao.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.boardgame.dao.IpartieDAO;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.JeuPossede;
import com.example.boardgame.metier.Partie;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class PartieDAOSQLite implements IpartieDAO {

    Context context;
    Dao<Partie, Integer> partieDao;
    MyOpenHelper base;

    public PartieDAOSQLite(Context context) {
        this.context=context;
        base=new MyOpenHelper(context);
        SQLiteDatabase db= base.getWritableDatabase();
        try {
            partieDao = DaoManager.createDao(new AndroidConnectionSource(db), Partie.class);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i("aaa","pb");
        }
    }

    @Override
    public void sauvegarderParties(List<Partie> parties) {
        try {
            partieDao.delete(parties);
            for(Partie partie : parties){
                ajouterPartie(partie);}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Partie> chargerParties() {
        try{
            return partieDao.queryForAll();
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ajouterPartie(Partie partie) {
        try{
            partieDao.create(partie);
            Log.i("aaaa","sql"+partie.getJeu());
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



    public List<Partie> getAllPartiebyGameId (int idJeu) {
        try {
            List<Partie> parties = partieDao.queryBuilder().where().eq("jeu_id", idJeu).query();
            return parties;
            //if (parties.size() != 0) {
            //    return parties;
            // } //else {
            //   return null;
            // }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNbvictoireJeu (Integer idJeu){
        List<Partie> parties = getAllPartiebyGameId(idJeu);
        int nbVictoires=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            if (victoire!=null){
                if (victoire==1){
                nbVictoires=nbVictoires+1;}
            }
        }
        return nbVictoires;
    }

    public int getNbvictoireEJeu (Integer idJeu){
        List<Partie> parties = getAllPartiebyGameId(idJeu);
        int nbVictoires=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            Boolean ecrasante = partie.getVictoireEcrasante();
            if (victoire!=null & ecrasante!=null){
                if (victoire==1 & ecrasante==true){
                    nbVictoires=nbVictoires+1;}
            }
        }
        return nbVictoires;
    }

    public int getNbdefaiteJeu (Integer idJeu){
        List<Partie> parties = getAllPartiebyGameId(idJeu);
        int nbDéfaites=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            if (victoire!=null){
                if (victoire==2){
                    nbDéfaites=nbDéfaites+1;}
            }
        }
        return nbDéfaites;
    }

    public int getNbdefaiteHJeu (Integer idJeu){
        List<Partie> parties = getAllPartiebyGameId(idJeu);
        int nbDéfaites=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            Boolean humiliante = partie.getDefaiteHumiliante();
            if (victoire!=null & humiliante!=null){
                if (victoire==2 & humiliante==true){
                    nbDéfaites=nbDéfaites+1;}
            }
        }
        return nbDéfaites;
    }

    public int getNbegaliteJeu (Integer idJeu) {
        List<Partie> parties = getAllPartiebyGameId(idJeu);
        int nbEgalite = 0;
        for (Partie partie : parties) {
            Integer victoire = partie.getVictoire();
            if (victoire != null ) {
                if (victoire == 3 ) {
                    nbEgalite = nbEgalite + 1;
                }
            }
        }
        return nbEgalite;
    }

    public int getNbvictoire (){
        List<Partie> parties = chargerParties();
        int nbVictoires=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            if (victoire!=null){
                if (victoire==1){
                    nbVictoires=nbVictoires+1;}
            }
        }
        return nbVictoires;
    }

    public int getNbvictoireE (){
        List<Partie> parties = chargerParties();
        int nbVictoires=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            Boolean ecrasante = partie.getVictoireEcrasante();
            if (victoire!=null & ecrasante !=null){
                if (victoire==1 & ecrasante){
                    nbVictoires=nbVictoires+1;}
            }
        }
        return nbVictoires;
    }

    public int getNbdefaite (){
        List<Partie> parties = chargerParties();
        int nbDéfaites=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            if (victoire!=null){
                if (victoire==2){
                    nbDéfaites=nbDéfaites+1;}
            }
        }
        return nbDéfaites;
    }

    public int getNbdefaiteH (){
        List<Partie> parties = chargerParties();
        int nbDéfaitesh=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            Boolean humiliante = partie.getDefaiteHumiliante();
            if (victoire!=null & humiliante!=null){
                if (victoire==2 & humiliante ==true){
                    nbDéfaitesh=nbDéfaitesh+1;}
            }
        }
        return nbDéfaitesh;
    }

    public int getNbegalite (){
        List<Partie> parties = chargerParties();
        int nbEgalites=0;
        for(Partie partie : parties){
            Integer victoire=partie.getVictoire();
            if (victoire!=null){
                if (victoire==3){
                    nbEgalites=nbEgalites+1;}
            }
        }
        return nbEgalites;
    }



    public int getMeilleurScoreJeu (Integer idJeu){
        List<Partie> parties = getAllPartiebyGameId(idJeu);
        Integer MeilleurScore=0;
        for(Partie partie : parties){
            Integer score=partie.getScore();
            if(score != null){
                if (score < MeilleurScore){
                    MeilleurScore=score;}
            }
        }
        return MeilleurScore;
    }

    public Integer getIdJeu(int idPartie){
        try {
            GenericRawResults<String[]> rawResults =
                    partieDao.queryRaw(
                            "select jeu_id from partie where id = " + idPartie);
            List<String[]> resultsList = rawResults.getResults();
            String[] result = resultsList.get(0);
            Integer idJeu = Integer.parseInt(result[0]);
            return idJeu;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Partie getPartiebyId(Integer id) {
        try {
        List<Partie> parties = partieDao.queryBuilder().where().eq("id", id).query();
        if (parties.size()!=0){
            return parties.get(0);
        }else {
            return null;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return null;
    }
    }

    @Override
    public  void deletePartiebyId(Integer id){
        try{ DeleteBuilder<Partie, Integer> deleteBuilder = partieDao.deleteBuilder();
            deleteBuilder.where().eq("id",id);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

