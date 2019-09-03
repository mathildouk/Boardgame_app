package com.example.boardgame.dao;

import com.example.boardgame.metier.Jeu;
import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface IjeuDAO {

    public void sauvegarderJeux(List<Jeu> jeux);

    public List<Jeu> chargerJeux();

    public void ajouterJeu(Jeu jeu);

    public Dao getJeuDao();

    public Jeu getJeubyId( Integer id);

    public List<Jeu> getJeuxbyName(String nomJeu);

    public  void deleteJeubyId(Integer id);
}


