package com.example.boardgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boardgame.dao.JeuAJouerDAOfactory;
import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.JeuJoueDAOfactory;
import com.example.boardgame.dao.PartieDAOfactory;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.JeuJoue;
import com.example.boardgame.metier.Partie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjouterPartieHistorique extends AppCompatActivity implements View.OnClickListener,  AdapterView.OnItemClickListener {

    Button ajouter=null;
    SearchView nomJeu;
    CheckBox VictoireOui,VictoireNon, Egalite, VictoireE, DefaiteH;
    EditText score;
    EditText remarque;
    DatePicker calendrier;
    Integer day;
    Integer month;
    Integer year;
    Date date;
    Jeu jeu=null;
    ListView listeJeux;
    private ArrayAdapter<Jeu> adapter;
    String nom;
    Boolean EVisible=false, HVisible=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_partie_historique);
        this.configureToolbar();

        ajouter = (Button) findViewById(R.id.ajouter);

        adapter = new ArrayAdapter<Jeu>(this, android.R.layout.simple_list_item_1);
        listeJeux = findViewById(R.id.rechercheJeu);
        listeJeux.setAdapter(adapter);
        listeJeux.setOnItemClickListener(this);

        nomJeu =(SearchView) findViewById(R.id.search);

        nomJeu.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                CharSequence message = nomJeu.getQuery();
                nom=message.toString();
                chargerDonnees();

                //if you want to collapse the searchview
                invalidateOptionsMenu();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        VictoireOui = findViewById(R.id.VictoireOui);
        VictoireOui.setOnClickListener(this);

        VictoireNon = findViewById(R.id.VictoireNon);
        VictoireNon.setOnClickListener(this);

        Egalite=findViewById(R.id.Egalite);
        Egalite.setOnClickListener(this);

        VictoireE=findViewById(R.id.VictoireEOui);
        VictoireE.setVisibility(View.GONE);

        DefaiteH=findViewById(R.id.DefaiteHOUI);
        DefaiteH.setVisibility(View.GONE);

        score = findViewById(R.id.score);

        remarque = findViewById(R.id.remarque);

        ajouter.setOnClickListener(this);

        calendrier = findViewById(R.id.simpleDatePicker);


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
        toolbar.setTitle("Ajouter une partie");
        setSupportActionBar(toolbar);
    }

    public  void chargerDonnees(){

        adapter.clear();
        List<Jeu> jeux=JeuDAOfactory.getJeuDAO(this).getJeuxbyName(nom);
        if (jeux.size()!= 0){
            for (Jeu jeu : jeux) {
                adapter.add(jeu);
            }
        }else{
            Jeu jeuTemp = new Jeu(0,"(Aucun jeu trouvé)");
            Log.i("test",""+jeuTemp.getNom());
            adapter.add(jeuTemp);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick (View v){

        switch (v.getId()) {

            case R.id.VictoireOui:
                VictoireNon.setChecked(false);
                Egalite.setChecked(false);
                if (EVisible){
                    EVisible=false;
                    VictoireE.setVisibility(View.GONE);
                    VictoireE.setChecked(false);
                }else{
                    EVisible=true;
                    VictoireE.setVisibility(View.VISIBLE);}
                if (HVisible){
                    HVisible=false;
                    DefaiteH.setVisibility(View.GONE);
                    DefaiteH.setChecked(false);
                }
                break;

            case R.id.VictoireNon:
                VictoireOui.setChecked(false);
                Egalite.setChecked(false);
                if (HVisible){
                    HVisible=false;
                    DefaiteH.setVisibility(View.GONE);
                    DefaiteH.setChecked(false);
                }else{
                    HVisible=true;
                    DefaiteH.setVisibility(View.VISIBLE);}
                if (EVisible){
                    EVisible=false;
                    VictoireE.setVisibility(View.GONE);
                    VictoireE.setChecked(false);
                }
                break;

            case R.id.Egalite:
                VictoireNon.setChecked(false);
                VictoireOui.setChecked(false);
                DefaiteH.setVisibility(View.GONE);
                DefaiteH.setChecked(false);
                HVisible=false;
                VictoireE.setVisibility(View.GONE);
                VictoireE.setChecked(false);
                HVisible=false;
                break;

            case R.id.ajouter :

                if(jeu==null){
                    Toast.makeText(this, "Vous devez rechercher et sélectionner un jeu", Toast.LENGTH_LONG).show();
                }
                try {

                    Partie partie = new Partie(jeu);
                    Log.i("aaaa", "ajout partie : " + partie.getJeu().toString());
                    Integer victoire = null;
                    if (VictoireOui.isChecked()) {
                        victoire = 1;
                        VictoireNon.setChecked(false);
                        Egalite.setChecked(false);
                        VictoireE.setVisibility(View.VISIBLE);
                    } else if (VictoireNon.isChecked()) {
                        victoire = 2;
                        VictoireOui.setChecked(false);
                        Egalite.setChecked(false);
                        DefaiteH.setVisibility(View.VISIBLE);
                    } else if (Egalite.isChecked()) {
                        victoire = 3;
                        VictoireNon.setChecked(false);
                        VictoireOui.setChecked(false);
                    }
                    partie.setVictoire(victoire);

                    if (VictoireE.isChecked()) {
                        partie.setVictoireEcrasante(true);
                    }

                    if (DefaiteH.isChecked()) {
                        partie.setVictoireEcrasante(true);
                    }

                    if (score.getText().toString().trim().length() > 0) {
                        try {
                            Integer points = Integer.parseInt(score.getText().toString());
                            partie.setScore(points);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    if (remarque.getText().toString().trim().length() > 0) {
                        String rq = remarque.getText().toString();
                        partie.setRemarque(rq);
                    }


                    day = calendrier.getDayOfMonth(); // get the selected day of the month
                    month = calendrier.getMonth() + 1;
                    year = calendrier.getYear();
                    Integer dateInt = year * 10000 + month * 100 + day;
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
                    try {
                        date = originalFormat.parse(dateInt.toString());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    partie.setDate(date);
                    PartieDAOfactory.getPartieDAO(this).ajouterPartie(partie);

                    finish();

                    JeuJoue jeuJoue = new JeuJoue(jeu);
                    JeuJoueDAOfactory.getJeuJoueDAO(this).ajouterJeu(jeuJoue);
                    finish();

                    if (JeuAJouerDAOfactory.getJeuAJouerDAO(this).getJeubyId(jeu.getIdentifiant()) != null) {
                        JeuAJouerDAOfactory.getJeuAJouerDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        List<Jeu> jeux = JeuDAOfactory.getJeuDAO(this).getJeuxbyName(nom);
        if (jeux.size() !=0){
            jeu=jeux.get(position);
            nomJeu.setQuery(jeu.getNom(),false);
            adapter.clear();
        }


    }
}
