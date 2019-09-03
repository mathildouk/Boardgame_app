package com.example.boardgame.metier;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "jeuAJouer")
public class JeuAJouer implements Comparable {

    @DatabaseField(foreign= true,unique=true)
    private Jeu jeu;

    public JeuAJouer(Jeu jeu) {
        this.jeu = jeu;
    }

    public  JeuAJouer()
    {

    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public String toString() {
        return jeu.toString();
    }

    @Override
    public int compareTo(Object o) {
        JeuAJouer j=(JeuAJouer) o;
        Log.i("aaa",""+getJeu());

        return getJeu().getNom().compareTo(j.getJeu().getNom());
    }

}


