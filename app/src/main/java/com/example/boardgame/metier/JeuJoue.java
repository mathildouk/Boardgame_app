package com.example.boardgame.metier;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "jeuJoue")
public class JeuJoue implements Comparable{

    @DatabaseField(foreign= true,unique=true)
    private Jeu jeu;

    public JeuJoue(Jeu jeu) {
        this.jeu = jeu;
    }

    public  JeuJoue()
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
        JeuJoue j=(JeuJoue) o;
        Log.i("aaa",""+getJeu());

        return getJeu().getNom().compareTo(j.getJeu().getNom());
    }
}


