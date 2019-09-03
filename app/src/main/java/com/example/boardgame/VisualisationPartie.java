package com.example.boardgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.PartieDAOfactory;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.Partie;

import java.net.URL;
import java.text.SimpleDateFormat;

public class VisualisationPartie extends AppCompatActivity {
    TextView nomJeu, date, victoire, score, remarque;
    Partie partie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualisation_partie);

        this.configureToolbar();


        int idPartie = getIntent().getExtras().getInt("id");
        setTitle("" + idPartie);

        partie= PartieDAOfactory.getPartieDAO(this).getPartiebyId(idPartie);

        Integer idJeu = PartieDAOfactory.getPartieDAO(this).getIdJeu(idPartie);
        Jeu jeu = JeuDAOfactory.getJeuDAO(this).getJeubyId(idJeu);
        partie.setJeu(jeu);

        nomJeu =(TextView) findViewById(R.id.nomJeu);

        date =(TextView) findViewById(R.id.date);

        victoire =(TextView) findViewById(R.id.victoire);

        score=(TextView) findViewById(R.id.score);

        remarque=(TextView) findViewById(R.id.remarques);

        chargerDonnees();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar

        getMenuInflater().inflate(R.menu.menu_visualisation_partie, menu);

        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        toolbar.setTitle("Partie");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
                case R.id.menu_activity_main_home:
                Intent intentMA = new Intent(this, MainActivity.class);
                startActivity(intentMA);
                return true;

            case R.id.menu_activity_main_delete:
                Intent intentD = new Intent(this, SupprimerPartie.class);
                intentD.putExtra("id", partie.getId());
                startActivity(intentD);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void chargerDonnees(){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String dateFormat = simpleDateFormat.format(partie.getDate());
        date.setText("Le "+dateFormat+"\n");
        nomJeu.setText("Vous avez joué au jeu suivant : "+partie.getJeu().getNom()+"."+"\n");



        if (partie.getVictoire() != null){

            victoire.setVisibility(View.VISIBLE);
            if(partie.getVictoire() == 1){
                victoire.setText("Vous avez gagné !");
                if(partie.getVictoireEcrasante()!=null){
                    if(partie.getVictoireEcrasante() == true){

                        victoire.setText("Vous avez gagné ! Et de manière écrasante !");
                    }
                }

            }
            else if(partie.getVictoire() == 2){
                victoire.setText("Vous avez perdu !");
                if(partie.getDefaiteHumiliante()!=null){
                    if(partie.getDefaiteHumiliante() == true){
                        victoire.setText("Vous avez perdu .. et de manière humiliante ..");
                    }
                }

            }
            else if(partie.getVictoire() == 3){
                victoire.setText("Vous avez fait égalité");
            }

        }else {
            victoire.setVisibility(View.GONE);
        }

        Log.i("test",""+partie.getScore());
        if (partie.getScore() != null){
            score.setVisibility(View.VISIBLE);
            score.setText(" Votre score était de : "+partie.getScore()+ " points.");
        }else {
            score.setVisibility(View.GONE);
        }

        if (partie.getRemarque() != null){
            remarque.setVisibility(View.VISIBLE);
            remarque.setText("\n"+"Remarques :"+"\n"+partie.getRemarque());
        }else {
            remarque.setVisibility(View.GONE);
        }
    }
}
