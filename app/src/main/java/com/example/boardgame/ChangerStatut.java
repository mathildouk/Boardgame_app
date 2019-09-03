package com.example.boardgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boardgame.dao.JeuAAcheterDAOfactory;
import com.example.boardgame.dao.JeuAJouerDAOfactory;
import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.JeuJoueDAOfactory;
import com.example.boardgame.dao.JeuPossedeDAOfactory;
import com.example.boardgame.dao.PartieDAOfactory;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.JeuAAcheter;
import com.example.boardgame.metier.JeuAJouer;
import com.example.boardgame.metier.JeuJoue;
import com.example.boardgame.metier.JeuPossede;

public class ChangerStatut extends AppCompatActivity implements View.OnClickListener {

    Button changer;
    CheckBox dejapossede, veutposseder, dejajoue, veutjouer;
    Jeu jeu;
    boolean veutJouer, joue, veutPosseder, possede;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changer_statut);
        this.configureToolbar();


        Integer idJeu = getIntent().getExtras().getInt("id");
        jeu = JeuDAOfactory.getJeuDAO(this).getJeubyId(idJeu);

        dejajoue = findViewById(R.id.dejajoue);

        veutjouer = findViewById(R.id.veutjouer);

        dejapossede = (CheckBox) findViewById(R.id.dejapossede);

        veutposseder = findViewById(R.id.veutposseder);

        changer = (Button) findViewById(R.id.changer);

        checked();

        dejajoue.setOnClickListener(this);
        veutjouer.setOnClickListener(this);
        dejapossede.setOnClickListener(this);
        veutposseder.setOnClickListener(this);
        changer.setOnClickListener(this);

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
        toolbar.setTitle("Changement du statut du jeu");
        setSupportActionBar(toolbar);
    }

    public void checked() {


        veutJouer = JeuAJouerDAOfactory.getJeuAJouerDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
        if (veutJouer) {
            veutjouer.setChecked(true);
        }

        joue = JeuJoueDAOfactory.getJeuJoueDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
        if (joue){
            dejajoue.setChecked(true);
        }

        veutPosseder = JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
        if (veutPosseder){
            veutposseder.setChecked(true);
        }

        possede = JeuPossedeDAOfactory.getJeuPossedeDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
        if (possede){
            dejapossede.setChecked(true);
        }

    }

    public void onClick (View v){

        switch (v.getId()) {
            case R.id.changer:
                if (dejapossede.isChecked() == false & veutposseder.isChecked() == false & dejajoue.isChecked() == false & veutjouer.isChecked() == false) {
                    Toast.makeText(this, "Vous n'avez pas changé le statut !", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, VisualisationJeu.class);
                    startActivity(intent);
                }
                if (dejajoue.isChecked()) {;
                    JeuJoue jeuJoue = new JeuJoue(jeu);
                    JeuJoueDAOfactory.getJeuJoueDAO(this).ajouterJeu(jeuJoue);
                    finish();
                    if (veutJouer) {
                        JeuAJouerDAOfactory.getJeuAJouerDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    break;
                }
                if (veutjouer.isChecked()) {
                    if (joue) {
                        if (PartieDAOfactory.getPartieDAO(this).getAllPartiebyGameId(jeu.getIdentifiant())==null){
                            JeuAJouer jeuAJouer = new JeuAJouer(jeu);
                            JeuAJouerDAOfactory.getJeuAJouerDAO(this).ajouterJeu(jeuAJouer);
                            finish();
                            JeuJoueDAOfactory.getJeuJoueDAO(this).deleteJeubyId(jeu.getIdentifiant());
                            finish();
                        }

                    }else{
                        JeuAJouer jeuAJouer = new JeuAJouer(jeu);
                        JeuAJouerDAOfactory.getJeuAJouerDAO(this).ajouterJeu(jeuAJouer);
                        finish();
                    }
                    break;
                }
                if (dejapossede.isChecked()) {
                    JeuPossede jeuPossede = new JeuPossede(jeu);
                    JeuPossedeDAOfactory.getJeuPossedeDAO(this).ajouterJeu(jeuPossede);
                    finish();
                    if (veutPosseder) {
                        JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    break;
                }
                if (veutposseder.isChecked()) {
                    JeuAAcheter jeuAAcheter = new JeuAAcheter(jeu);
                    JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).ajouterJeu(jeuAAcheter);
                    finish();
                    if (possede) {
                        JeuPossedeDAOfactory.getJeuPossedeDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    break;
                }

            case R.id.veutposseder :
                Log.i("Click","onclick");
                dejapossede.setChecked(false);
                break;

            case R.id.dejapossede :
                Log.i("Click","onclick");
                veutposseder.setChecked(false);
                break;

            case R.id.veutjouer :
                Log.i("Click","onclick");
                dejajoue.setChecked(false);
                break;

            case R.id.dejajoue :
                Log.i("Click","onclick");
                veutjouer.setChecked(false);
                break;

        }
    }


}
