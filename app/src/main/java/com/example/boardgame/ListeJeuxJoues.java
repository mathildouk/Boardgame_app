package com.example.boardgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.JeuJoueDAOfactory;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.JeuJoue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListeJeuxJoues extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemClickListener {

    private ArrayAdapter<Jeu> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_jeux);

        //1 - Configuring Toolbar
        this.configureToolbar();


        adapter = new ArrayAdapter<Jeu>(this, android.R.layout.simple_list_item_1);
        ListView liste = (ListView) findViewById(R.id.listeJeux);
        liste.setAdapter(adapter);
        chargerDonnees();
        Button ajouterJeu = findViewById(R.id.ajouterJeu);
        Button jeuxPossedes = findViewById(R.id.JeuxPossedes);
        Button jeuxAJouer = findViewById(R.id.JeuxAJouer);
        Button jeuxJoues = findViewById(R.id.JeuxJoues);
        Button jeuxAAcheter= findViewById(R.id.JeuxAAcheter);
        ajouterJeu.setOnClickListener(this);
        jeuxPossedes.setOnClickListener(this);
        jeuxAAcheter.setOnClickListener(this);
        jeuxAJouer.setOnClickListener(this);
        jeuxJoues.setOnClickListener(this);
        liste.setOnItemClickListener(this);

        jeuxJoues.setBackgroundColor(5);
    }

    private void configureToolbar(){
        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }


    private void chargerDonnees() {
        adapter.clear();
        //Liste générée à la volée
        List<JeuJoue> liste = new ArrayList<JeuJoue>();

        //Liste chargée à partir du fichier
        liste = JeuJoueDAOfactory.getJeuJoueDAO(this).chargerJeux();
        List<Jeu> listeJeu = new ArrayList<Jeu>();

        //Liste chargée à partir du fichier
        liste = JeuJoueDAOfactory.getJeuJoueDAO(this).chargerJeux();

        for (JeuJoue jeu : liste) {
            try{
                int id= jeu.getJeu().getIdentifiant();
                jeu.setJeu(JeuDAOfactory.getJeuDAO(this).getJeubyId(id));
                listeJeu.add(JeuDAOfactory.getJeuDAO(this).getJeubyId(id));
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        Collections.sort(liste);
        Collections.sort(listeJeu);
        for (Jeu jeu :listeJeu){
            adapter.add(jeu);
        }
        adapter.notifyDataSetChanged();
    }





    @Override
    protected void onResume() {
        super.onResume();
        chargerDonnees();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ajouterJeu:
                Intent intentA = new Intent(this, AjouterJeu.class);
                startActivity(intentA);
                chargerDonnees();
                break;

            case R.id.JeuxPossedes:
                Intent intentP = new Intent(this, ListeJeuxPossedes.class);
                startActivity(intentP);
                break;

            case R.id.JeuxJoues:
                chargerDonnees();
                break;

            case R.id.JeuxAJouer:
                Intent intentAJ = new Intent(this, ListeJeuxAJouer.class);
                startActivity(intentAJ);
                break;

            case R.id.JeuxAAcheter:
                Intent intentAA = new Intent(this, ListeJeuxAAcheter.class);
                startActivity(intentAA);
                break;

        }

    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        List<JeuJoue> jeux = JeuJoueDAOfactory.getJeuJoueDAO(this).chargerJeux();
        for (JeuJoue jeu : jeux) {
            try{
                int idJeu= jeu.getJeu().getIdentifiant();
                jeu.setJeu(JeuDAOfactory.getJeuDAO(this).getJeubyId(idJeu));
            }
            catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        Collections.sort(jeux);
        Intent intent = new Intent(this,VisualisationJeu.class);
        intent.putExtra("id", jeux.get(position).getJeu().getIdentifiant());
        intent.putExtra("dejaBDD", true);
        Log.i("intent", ""+jeux.get(position).getJeu().getIdentifiant());
        startActivity(intent);
    }


}