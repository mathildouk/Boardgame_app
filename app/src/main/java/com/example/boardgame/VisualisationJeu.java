package com.example.boardgame;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.PartieDAOfactory;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.Partie;
import com.example.boardgame.metier.boardgames;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class VisualisationJeu extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    TextView id, nom, annee, statJeu,description, infosJeu;
    Button showDescription, showHistorique, ajouterPartie, showStat;
    ListView historique;
    boolean historiqueVisible = false, statVisible =false, descriptionVisible=false;
    private ArrayAdapter<Partie> adapter;
    Jeu jeu;
    ImageView Image;
    URL urlImage = null;
    InputStream api;
    XmlMapper xmlMapper;
    boardgames boardgames;
    Integer idJeu;
    String nomJeu;
    Bitmap bitmap;
    Boolean dejaBdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualisation_jeu);
        idJeu = getIntent().getExtras().getInt("id");
        dejaBdd = getIntent().getExtras().getBoolean("dejaBDD");
        Log.i("aaa", "" + dejaBdd);
        setTitle("" + idJeu);// Je sais pas trop à quoi ça sert ...
        this.configureToolbar();
        adapter = new ArrayAdapter<Partie>(this, android.R.layout.simple_list_item_1);
        nom = (TextView) findViewById(R.id.nomJeu);

        annee = (TextView) findViewById(R.id.annee);

        infosJeu = (TextView) findViewById((R.id.infosJeu));

        showDescription =(Button) findViewById(R.id.showDescription);

        description =(TextView) findViewById(R.id.descriptionJeu);
        description.setMovementMethod(new ScrollingMovementMethod());
        description.setVisibility(View.GONE);

        showStat=(Button)findViewById(R.id.showStatJeu);

        statJeu=(TextView) findViewById(R.id.statJeu);
        statJeu.setVisibility(View.GONE);

        showHistorique = (Button) findViewById(R.id.parties);

        historique = (ListView) findViewById(R.id.historique);
        historique.setAdapter(adapter);
        historique.setVisibility(View.GONE);

        ajouterPartie = (Button)findViewById(R.id.ajouterPartie);




        //Le jeu est deja enregistré dans la base de donnée
        if (dejaBdd == true){
            jeu = JeuDAOfactory.getJeuDAO(this).getJeubyId(idJeu);


            chargerDonnees();
            chargerStatistiques();
        }
        //Le jeu n'est pas enregistré dans la base de données. Toutes les otptions ne sont pas dispo et ils font aller se connecter à l'api
        else{
            nomJeu = getIntent().getExtras().getString("nom");
            showHistorique.setVisibility(View.INVISIBLE);
            showStat.setVisibility(View.INVISIBLE);
            historique.setVisibility(View.INVISIBLE);
            ajouterPartie.setVisibility(View.INVISIBLE);

            try {
                jeu = RechercheInfoAPI();
            } catch (IOException e) {
                e.printStackTrace();
            }






        }



        //String anneeparenthese = "("+jeu.getYearpublished().toString()+")";
        /*nom.setText(jeu.getNom()+" "+anneeparenthese);*/

        //Image


        showDescription.setOnClickListener(this);
        showStat.setOnClickListener(this);
        showHistorique.setOnClickListener(this);
        historique.setOnItemClickListener(this);
        ajouterPartie.setOnClickListener(this);



        chargerInformations();
        try {

            urlImage = new URL(jeu.getThumbnail());

        } catch (MalformedURLException e) {
            e.printStackTrace();

        }

        Image = (ImageView) findViewById(R.id.image);
        // Manifest.permission.INTERNET;
        downloadImage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        Log.i("aaa","menu"+dejaBdd.toString());
        if (dejaBdd == true) {
            Log.i("aaa","menu1");
            getMenuInflater().inflate(R.menu.menu_visualisation_jeu, menu);
        }
        else{
            Log.i("aaa","menu2");
            getMenuInflater().inflate(R.menu.menu_principal, menu);
        }
        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        toolbar.setTitle("Jeu");
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_setting:
                Intent intentCS = new Intent(this, ChangerStatut.class);
                intentCS.putExtra("id", jeu.getIdentifiant());
                startActivity(intentCS);
                return true;

            case R.id.menu_activity_main_home:
                Intent intentMA = new Intent(this, MainActivity.class);
                startActivity(intentMA);
                return true;

            case R.id.menu_activity_main_delete:
                Intent intentD = new Intent(this, SupprimerJeu.class);
                intentD.putExtra("id", jeu.getIdentifiant());
                startActivity(intentD);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void chargerDonnees() {
        adapter.clear();
        //Liste générée à la volée
        List<Partie> liste = new ArrayList<Partie>();
        ;

        //Liste chargée à partir du fichier
        liste = PartieDAOfactory.getPartieDAO(this).getAllPartiebyGameId(jeu.getIdentifiant());

        for (Partie partie : liste) {
               try {
                   PartieDAOfactory.getPartieDAO(this).getIdJeu (partie.getId());

                partie.setJeu(jeu);
                adapter.add(partie);
            } catch (NullPointerException e) {

                 e.printStackTrace();
            }
          }
        adapter.notifyDataSetChanged();
    }

    public  void chargerStatistiques() {
        List<Partie> parties = PartieDAOfactory.getPartieDAO(this).getAllPartiebyGameId(jeu.getIdentifiant());
        Collections.sort(parties);
        int nbPartie = parties.size();
        int nbVictoire = PartieDAOfactory.getPartieDAO(this).getNbvictoireJeu(jeu.getIdentifiant());
        int nbVictoireE = PartieDAOfactory.getPartieDAO(this).getNbvictoireEJeu(jeu.getIdentifiant());
        int nbDefaites = PartieDAOfactory.getPartieDAO(this).getNbdefaiteJeu(jeu.getIdentifiant());
        int nbDefaitesH = PartieDAOfactory.getPartieDAO(this).getNbdefaiteHJeu(jeu.getIdentifiant());
        int nbEgalites = PartieDAOfactory.getPartieDAO(this).getNbegaliteJeu(jeu.getIdentifiant());
        int MeilleurScore = PartieDAOfactory.getPartieDAO(this).getMeilleurScoreJeu(jeu.getIdentifiant());
        String stats = "";
        if (nbPartie != 0){
            stats = stats + "\n" +
                    "Nombre de parties : " + nbPartie ;
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateFormat = simpleDateFormat.format(parties.get(0).getDate());
            stats = stats + "\nDernière partie jouée le : "+dateFormat;
            if (nbVictoire != 0) {
                stats = stats + "\n" +
                        "Nombre de victoires : " + nbVictoire + " (" + nbVictoire * 100 / nbPartie + "%)";
                if (nbVictoireE != 0) {
                    stats = stats + "\n dont " + nbVictoireE + " victoires écrasantes";
                }
            }
            else {
                stats = stats + "\n" + "Aucune victoire";

            }
            if (nbEgalites != 0) {
                stats = stats + "\n" +
                        "Nombre d'égalités : " + nbEgalites + " (" + nbEgalites * 100 / nbPartie + "%)";
            } else {
                stats = stats + "\n" + "Aucune égalité";
            }

            if (nbDefaites != 0) {
                stats = stats + "\n" +
                        "Nombre de défaites : " + nbVictoire + " (" + nbVictoire * 100 / nbPartie + "%)" + "\n";
                if (nbDefaitesH != 0) {
                    stats = stats + "\n dont " + nbDefaitesH + " victoires humiliantes ..";
                }
            } else {
                stats = stats + "\n" + "Aucune défaite";
            }
            if(MeilleurScore!=0){
                stats = stats + "\n"+"Meilleur score :"+MeilleurScore;
            }


        }
        else{
            stats = stats +"aucune partie jouée";
        }




        statJeu.setText(stats);

    }

    public void chargerInformations(){
        String age = jeu.getAge();
        String minPlayer =jeu.getMinplayers();
        String maxPlayer =jeu.getMaxplayers();
        String time =jeu.getPlayingtime();

        String anneeparenthese = "("+jeu.getYearpublished().toString()+")";
        String infos ="Age minimum : "+age+" ans"+"\n"+
                "Durée moyenne de jeu : "+time+" minutes"+"\n"+
                "Nombre minimal de joueurs : "+minPlayer+"\n"+
                "Nombre maximal de joueurs : "+maxPlayer+"\n";
        String descriptionString=jeu.getDescription().replaceAll("<br/>","\n");

        nom.setText(jeu.getNom());

        annee.setText(" "+anneeparenthese);

        infosJeu.setText("\n"+infos);

        description.setText(descriptionString);
    }


    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.parties:
                if (!historiqueVisible) {
                    chargerDonnees();
                    historique.setVisibility(View.VISIBLE);
                    historiqueVisible = true;
                } else {
                    historique.setVisibility(View.GONE);
                    historiqueVisible = false;
                }
                break;

            case R.id.ajouterPartie:
                Intent intentA = new Intent(this, AjouterPartie.class);
                intentA.putExtra("id", jeu.getIdentifiant());
                startActivity(intentA);
                chargerDonnees();
                break;


            case R.id.showStatJeu :
                if (!statVisible) {
                    chargerStatistiques();
                    statJeu.setVisibility(View.VISIBLE);
                    statVisible = true;
                } else {
                    statJeu.setVisibility(View.GONE);
                    statVisible = false;
                }
                break;

            case R.id.showDescription:
                if (!descriptionVisible) {
                    //chargerStatistiques();
                    description.setVisibility(View.VISIBLE);
                    descriptionVisible = true;
                } else {
                    description.setVisibility(View.GONE);
                    descriptionVisible = false;
                }
                break;


        }

    }

    private void downloadImage() {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) urlImage.openConnection();
                    InputStream inputStream = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }


        }
    });
        thread.start();
        try{
            thread.join();
            Image.setImageBitmap(bitmap);

        }
         catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        List<Partie> parties = PartieDAOfactory.getPartieDAO(this).getAllPartiebyGameId(jeu.getIdentifiant());
        Intent intentP = new Intent(this,VisualisationPartie.class);
        intentP.putExtra("id", parties.get(position).getId());
        Log.i("intent",""+parties.get(position).getId());
        startActivity(intentP);
    }

    public Jeu RechercheInfoAPI() throws IOException {


        //Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Integer id = jeu.getIdentifiant();
                // StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build()); //à ne pas faire en vrai
                URL url = null;
                try {
                    url = new URL("https://www.boardgamegeek.com/xmlapi/boardgame/"+idJeu.toString()+"&apikey=79c327454a97a8faa33d7b06f3d86e364da49e9c8766a0e10439ee47");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    api = connection.getInputStream();
                    xmlMapper = new XmlMapper();
                    String xml = api.toString();
                    Log.i("aaaa","fin lire xml");
                    xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    boardgames = xmlMapper.readValue(api, boardgames.class);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        jeu = new Jeu(idJeu,nomJeu);


        try{
            thread.join();

            jeu.setYearpublished(boardgames.getBoardgame().getYearpublished());
            jeu.setMinplayers(boardgames.getBoardgame().getMinplayers());
            jeu.setMaxplayers(boardgames.getBoardgame().getMaxplayers());
            jeu.setPlayingtime(boardgames.getBoardgame().getPlayingtime());
            jeu.setMinplaytime(boardgames.getBoardgame().getMinplaytime());
            jeu.setMaxplaytime(boardgames.getBoardgame().getMaxplaytime());
            jeu.setAge(boardgames.getBoardgame().getAge());
            jeu.setDescription(boardgames.getBoardgame().getDescription());
            jeu.setThumbnail(boardgames.getBoardgame().getThumbnail());
            jeu.setImage(boardgames.getBoardgame().getImage());

            return jeu;

        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


}