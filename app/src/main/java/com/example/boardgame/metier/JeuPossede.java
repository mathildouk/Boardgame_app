package com.example.boardgame.metier;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "jeuPossede")
public class JeuPossede implements Comparable{

    @DatabaseField(foreign= true,unique=true)
    private Jeu jeu;

    public JeuPossede(Jeu jeu) {
        this.jeu = jeu;
    }

    public  JeuPossede()
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
        JeuPossede j=(JeuPossede)o;
        Log.i("aaa",""+getJeu());

        return getJeu().getNom().compareTo(j.getJeu().getNom());
    }




}



