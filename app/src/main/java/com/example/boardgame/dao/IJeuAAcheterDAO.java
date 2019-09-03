package com.example.boardgame.dao;

import com.example.boardgame.metier.JeuAAcheter;
import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface IJeuAAcheterDAO {
    public void sauvegarderJeux(List<JeuAAcheter> jeux);

    public List<JeuAAcheter> chargerJeux();

    public void ajouterJeu(JeuAAcheter jeu);

    public JeuAAcheter getJeubyId(Integer id);

    public Dao getJeuDao();

    public  void deleteJeubyId(Integer id);

    public Integer nbJeux();
}
