package com.example.boardgame.dao;

import com.example.boardgame.metier.JeuPossede;
import com.j256.ormlite.dao.Dao;

import java.util.List;

public interface IJeuPossedeDAO {
    public void sauvegarderJeux(List<JeuPossede> jeux);

    public List<JeuPossede> chargerJeux();

    public void ajouterJeu(JeuPossede jeu);

    public JeuPossede getJeubyId(Integer id);

    public Dao getJeuDao();

    public  void deleteJeubyId(Integer id);

    public Integer nbJeux();


}
