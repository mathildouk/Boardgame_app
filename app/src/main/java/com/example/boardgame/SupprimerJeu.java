package com.example.boardgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.boardgame.dao.JeuAAcheterDAOfactory;
import com.example.boardgame.dao.JeuAJouerDAOfactory;
import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.JeuJoueDAOfactory;
import com.example.boardgame.dao.JeuPossedeDAOfactory;
import com.example.boardgame.metier.JeuAJouer;
import com.example.boardgame.metier.JeuJoue;
import com.example.boardgame.metier.JeuPossede;

public class SupprimerJeu extends AppCompatActivity implements View.OnClickListener {

    Button oui,non;
    Integer idJeu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supprimer_jeu);
        this.configureToolbar();
        idJeu = getIntent().getExtras().getInt("id");
        oui = findViewById(R.id.oui);
        oui.setOnClickListener(this);
        non = findViewById(R.id.non);
        non.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        toolbar.setTitle("Jeu");
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oui:
                JeuPossedeDAOfactory.getJeuPossedeDAO(this).deleteJeubyId(idJeu);
                JeuJoueDAOfactory.getJeuJoueDAO(this).deleteJeubyId(idJeu);
                JeuAJouerDAOfactory.getJeuAJouerDAO(this).deleteJeubyId(idJeu);
                JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).deleteJeubyId(idJeu);
                JeuDAOfactory.getJeuDAO(this).deleteJeubyId(idJeu);

                Intent intent = new Intent(this, ListeJeuxPossedes.class);
                startActivity(intent);
                break;
            case R.id.non:
                finish();
                break;
        }

    }
}
