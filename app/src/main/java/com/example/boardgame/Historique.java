package com.example.boardgame;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.PartieDAOfactory;

import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.Partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Historique extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private ArrayAdapter<Partie> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historique);

        //1 - Configuring Toolbar
        this.configureToolbar();

    adapter = new ArrayAdapter<Partie>(this, android.R.layout.simple_list_item_1);
    ListView liste = (ListView) findViewById(R.id.listeParties);
    liste.setAdapter(adapter);
    chargerDonnees();
    Button ajouterPartie = findViewById(R.id.ajouterPartie);
        ajouterPartie.setOnClickListener(this);

        liste.setOnItemClickListener(this);

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
        List<Partie> liste = new ArrayList<Partie>();

       // Liste chargée à partir du fichier

        liste = PartieDAOfactory.getPartieDAO(this).chargerParties();

        Collections.sort(liste);
        for (Partie partie : liste) {
            Integer idJeu=PartieDAOfactory.getPartieDAO(this).getIdJeu(partie.getId());
            Jeu jeu = JeuDAOfactory.getJeuDAO(this).getJeubyId(idJeu);
            partie.setJeu(jeu);
            adapter.add(partie);
        }
        adapter.notifyDataSetChanged();

    }



    @Override
    protected void onResume() {
        super.onResume();
        chargerDonnees();
        Log.i("historique","onresume");
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,AjouterPartieHistorique.class);
        startActivity(intent);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        List<Partie> parties = PartieDAOfactory.getPartieDAO(this).chargerParties();
        Intent intentP = new Intent(this,VisualisationPartie.class);
        intentP.putExtra("id", parties.get(position).getId());
        startActivity(intentP);
    }


}
