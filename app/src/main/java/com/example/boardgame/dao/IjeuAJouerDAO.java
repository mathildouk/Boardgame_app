package com.example.boardgame.dao;

import com.example.boardgame.metier.JeuAJouer;
import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface IjeuAJouerDAO {

    public void sauvegarderJeux(List<JeuAJouer> jeux);

    public List<JeuAJouer> chargerJeux();

    public void ajouterJeu(JeuAJouer jeu);

    public JeuAJouer getJeubyId(Integer id);

    public Dao getJeuDao();

    public  void deleteJeubyId(Integer id);

    public Integer nbJeux();
}
