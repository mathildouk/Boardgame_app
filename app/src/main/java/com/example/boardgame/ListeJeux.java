package com.example.boardgame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;


import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.metier.Jeu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListeJeux extends AppCompatActivity implements View.OnClickListener {

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
        ajouterJeu.setOnClickListener(this);
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
        List<Jeu> liste = new ArrayList<Jeu>();
        liste.add(new Jeu(1,"Jeu 1"));

        //Liste chargée à partir du fichier
        liste = JeuDAOfactory.getJeuDAO(this).chargerJeux();
        Collections.sort(liste);
        for (Jeu jeu : liste) {
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
        Intent intent = new Intent(this,AjouterJeu.class);
        startActivity(intent);

    }


}

