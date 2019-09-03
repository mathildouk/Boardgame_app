package com.example.boardgame.dao;

import com.example.boardgame.metier.Partie;

import java.util.List;

public interface IpartieDAO {

    public void sauvegarderParties(List<Partie> parties);

    public List<Partie> chargerParties();

    public void ajouterPartie(Partie partie);


    //   public Integer nbParties();

    public List<Partie> getAllPartiebyGameId (int idJeu);

    //  public Integer nbParties();

    public Integer getIdJeu(int idPartie);

    public int getNbvictoireJeu (Integer idJeu);

    public int getNbvictoire ();

    public int getNbdefaiteJeu (Integer idJeu);

    public int getNbdefaite ();

    public int getMeilleurScoreJeu (Integer idJeu);

    public Partie getPartiebyId(Integer id);

    public  void deletePartiebyId(Integer id);

    public int getNbvictoireE ();

    public int getNbdefaiteH ();

    public int getNbegalite ();

    public int getNbvictoireEJeu (Integer id);

    public int getNbdefaiteHJeu (Integer id);

    public int getNbegaliteJeu (Integer id);


}

