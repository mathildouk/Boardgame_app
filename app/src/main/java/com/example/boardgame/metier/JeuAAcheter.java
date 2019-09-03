package com.example.boardgame.metier;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "jeuAAcheter")
public class JeuAAcheter implements Comparable {

    @DatabaseField(foreign= true, unique=true)
    private Jeu jeu;

    public JeuAAcheter(Jeu jeu) {
        this.jeu = jeu;
    }

    public  JeuAAcheter()
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
        JeuAAcheter j=(JeuAAcheter)o;
        Log.i("aaa",""+getJeu());

        return getJeu().getNom().compareTo(j.getJeu().getNom());
    }
}

