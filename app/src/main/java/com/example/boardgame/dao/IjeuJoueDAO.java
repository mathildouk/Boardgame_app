package com.example.boardgame.dao;

import com.example.boardgame.metier.JeuJoue;
import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface IjeuJoueDAO {
    public void sauvegarderJeux(List<JeuJoue> jeux);

    public List<JeuJoue> chargerJeux();

    public void ajouterJeu(JeuJoue jeu);

    public JeuJoue getJeubyId(Integer id);

    public Dao getJeuDao();

    public  void deleteJeubyId(Integer id);

    public Integer nbJeux();
}
