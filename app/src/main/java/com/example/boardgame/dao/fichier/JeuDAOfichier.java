package com.example.boardgame.dao.fichier;

import android.content.Context;
import android.util.Log;

import com.example.boardgame.dao.IjeuDAO;
import com.example.boardgame.metier.Jeu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class JeuDAOfichier{
    public static final String FILENAME = "jeux.txt";
    Context context;

    public JeuDAOfichier(Context context) {
        this.context = context;
    }

    public void sauvegarderJeux(List<Jeu> jeux) {
        try {
            FileOutputStream stream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(stream);
            for (Jeu jeu : jeux) {
                writer.println(jeu.getIdentifiant()+"#"+jeu.getNom().replaceAll(System.getProperty("line.separator"), "[retourALaLigne]"));
            }
            writer.close();
        }
        catch (Exception e) {
            Log.e("sauvegarderJeux fichier", "",e);
        }
    }


    public List<Jeu> chargerJeux() {
        List<Jeu> liste = new ArrayList<Jeu>();
        try {
            FileInputStream stream = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String ligne = null;
            while ((ligne = reader.readLine()) != null) {
                String[] data = ligne.split("#");

                Jeu jeu = new Jeu(Integer.parseInt(data[0]),data[1]);
                liste.add(jeu);
            }
            reader.close();
        }
        catch (Exception e) {
            Log.e("chargerJeux fichier", "",e);
        }
        return liste;
    }

    public void ajouterJeu(Jeu jeu) {
        List<Jeu> jeux = chargerJeux();
        jeux.add(jeu);
        sauvegarderJeux(jeux);

    }


}
