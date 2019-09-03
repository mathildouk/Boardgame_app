package com.example.boardgame;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.boardgame.AsyncTask.AsyncTaskAPI1;
import com.example.boardgame.dao.JeuAAcheterDAOfactory;
import com.example.boardgame.dao.JeuAJouerDAOfactory;
import com.example.boardgame.dao.JeuDAOfactory;
import com.example.boardgame.dao.JeuJoueDAOfactory;
import com.example.boardgame.dao.JeuPossedeDAOfactory;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.JeuAAcheter;
import com.example.boardgame.metier.JeuAJouer;
import com.example.boardgame.metier.JeuJoue;
import com.example.boardgame.metier.JeuPossede;
import com.example.boardgame.metier.boardgames;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import static android.os.Build.ID;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class AjouterJeu extends AppCompatActivity implements View.OnClickListener {

    Button ajouter = null, infos;
    CheckBox dejapossede, veutposseder, dejajoue, veutjouer ;
    ListView listejeux;
    private ArrayAdapter<Jeu> adapter;
    ArrayList<Jeu> jeux;
    SearchView search;
    Integer idJeu=null;
    String nomJeu;
    CharSequence message;
    InputStream page;
    Jeu jeu;
    XmlMapper xmlMapper;
    InputStream api;
    boardgames boardgames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_jeu);

        this.configureToolbar();

        ajouter = (Button) findViewById(R.id.ajouter);

        dejajoue = findViewById(R.id.dejajoue);
        dejajoue.setOnClickListener(this);

        veutjouer = findViewById(R.id.veutjouer);
        veutjouer.setOnClickListener(this);


        dejapossede = (CheckBox) findViewById(R.id.dejapossede);
        dejapossede.setOnClickListener(this);

        veutposseder = findViewById(R.id.veutposseder);
        veutposseder.setOnClickListener(this);

        ajouter.setOnClickListener(this);

        search = findViewById(R.id.search);

        infos = findViewById(R.id.infos);
        infos.setOnClickListener(this);







        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                message = search.getQuery();

                //Thread
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        page = ConnexionUrl(message);
                        parseXML(page);


                    }
                });
                thread.start();

                try{
                        thread.join();
                    chargerDonnees();
                    }
                   catch (InterruptedException e) {
                        e.printStackTrace();
                    }





                //if you want to collapse the searchview
                invalidateOptionsMenu();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        adapter = new ArrayAdapter<Jeu>(this, android.R.layout.simple_list_item_1);
        listejeux = findViewById(R.id.rechercheJeu);
        listejeux.setAdapter(adapter);


        listejeux.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                v.setSelected(true);

                jeu = (Jeu) listejeux.getItemAtPosition(position);
                idJeu = jeu.getIdentifiant();
                nomJeu = jeu.getNom();
            }
        });
    }


                private void parseXML (InputStream is){



                    XmlPullParserFactory parserFactory;
                    try {

                        parserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = parserFactory.newPullParser();


                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(is, null);


                        processParsing(parser);

                    } catch (XmlPullParserException e) {
                        Log.i("ajouterJeu", "parseXML Exception 1");
                    } catch (IOException e) {
                        Log.i("ajouterJeu", "parseXML Exception 2");
                    }
                }

                private void processParsing (XmlPullParser parser) throws IOException, XmlPullParserException {
                    jeux = new ArrayList<>();
                    int eventType = parser.getEventType();
                    Jeu currentJeu = null;

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String eltName = null;

                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                eltName = parser.getName();
                                String attr = parser.getAttributeValue(null, "objectid");
                                if (eltName.equalsIgnoreCase("boardgame")) {
                                    currentJeu = new Jeu(null, "");
                                    //   Integer iden = currentJeu.getIdentifiant();
                                    // String ide = iden.toString();
                                    //    Log.i("id", "identifiant par défaut :"+ide);
                                    currentJeu.setIdentifiant(Integer.parseInt(attr));

                                    jeux.add(currentJeu);

                                }
                                if ("name".equals(eltName)) {
                                    currentJeu.setNom(parser.nextText());
                                }
                                break;

                        }

                        eventType = parser.next();
                    }

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
        toolbar.setTitle("Ajouter un jeu");
        setSupportActionBar(toolbar);
    }

private void chargerDonnees () {
        Log.i("jeux", "charger");
        adapter.clear();
        if (jeux.isEmpty()) {
        Toast.makeText(this, "Pas de jeux", Toast.LENGTH_LONG).show(); //à améliorer
        }

        //Liste chargée à partir du fichier
        for (Jeu jeu : jeux) {
        adapter.add(jeu);
        }
        adapter.notifyDataSetChanged();
        }

    public void onClick (View v){

        switch (v.getId()) {

            case R.id.ajouter:
                if(idJeu==null){
                    Toast.makeText(this, "Vous devez rechercher et sélectionner un jeu", Toast.LENGTH_LONG).show();
                }

                else if (dejapossede.isChecked() == false & veutposseder.isChecked() == false & dejajoue.isChecked() == false & veutjouer.isChecked() == false) {
                    Toast.makeText(this, "Vous devez sélectionner au moins une catégorie", Toast.LENGTH_LONG).show();
                }

                else if (dejajoue.isChecked()) {
                    Jeu jeu = new Jeu(idJeu, nomJeu);
                    try {
                        jeu = Remplissage(jeu);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JeuJoue jeuJoue = new JeuJoue(jeu);
                    JeuJoueDAOfactory.getJeuJoueDAO(this).ajouterJeu(jeuJoue);
                    finish();
                    Boolean veutJouer = JeuAJouerDAOfactory.getJeuAJouerDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
                    finish();
                    if (veutJouer) {
                        JeuAJouerDAOfactory.getJeuAJouerDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    JeuDAOfactory.getJeuDAO(this).ajouterJeu(jeu);
                    finish();
                }
                else if (veutjouer.isChecked()) {
                    Jeu jeu = new Jeu(idJeu, nomJeu);
                    try {
                        jeu = Remplissage(jeu);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JeuAJouer jeuAJouer = new JeuAJouer(jeu);
                    JeuAJouerDAOfactory.getJeuAJouerDAO(this).ajouterJeu(jeuAJouer);
                    finish();
                    Boolean joue = JeuJoueDAOfactory.getJeuJoueDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
                    finish();
                    if (joue) {
                        JeuJoueDAOfactory.getJeuJoueDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    JeuDAOfactory.getJeuDAO(this).ajouterJeu(jeu);
                    finish();
                }
                else if (dejapossede.isChecked()) {

                    Jeu jeu =new Jeu(idJeu, nomJeu);
                    try {
                        jeu = Remplissage(jeu);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    JeuPossede jeuPossede = new JeuPossede(jeu);

                    JeuPossedeDAOfactory.getJeuPossedeDAO(this).ajouterJeu(jeuPossede);
                    finish();
                    Boolean veutPosseder = JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
                    finish();

                    if (veutPosseder) {
                        JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    JeuDAOfactory.getJeuDAO(this).ajouterJeu(jeu);
                    finish();
                }
                else if (veutposseder.isChecked()) {
                    Jeu jeu =new Jeu(idJeu, nomJeu);
                    try {
                        jeu = Remplissage(jeu);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JeuAAcheter jeuAAcheter = new JeuAAcheter(jeu);
                    JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).ajouterJeu(jeuAAcheter);
                    finish();
                    Boolean possede = JeuPossedeDAOfactory.getJeuPossedeDAO(this).getJeubyId(jeu.getIdentifiant()) != null;
                    finish();
                    if (possede) {
                        JeuPossedeDAOfactory.getJeuPossedeDAO(this).deleteJeubyId(jeu.getIdentifiant());
                        finish();
                    }
                    JeuDAOfactory.getJeuDAO(this).ajouterJeu(jeu);
                    finish();
                }
                break;

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

            case R.id.infos :
                if(idJeu==null){
                    Toast.makeText(this, "Vous devez rechercher et sélectionner un jeu", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(this,VisualisationJeu.class);
                    intent.putExtra("id", idJeu);
                    intent.putExtra("nom", nomJeu);
                    intent.putExtra("dejaBDD", false);
                    Log.i("aaa", ""+idJeu);
                    startActivity(intent);
                }


        }
    }


    public Jeu Remplissage(Jeu jeu) throws IOException {


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


        protected void onResume () {
        super.onResume();
        //chargerDonnees();
    }

    public InputStream ConnexionUrl(CharSequence recherche){
        Log.i("aaaa","connexion url");
        try {
            URL url = new URL("https://www.boardgamegeek.com/xmlapi/search?search=" + recherche + "&apikey=79c327454a97a8faa33d7b06f3d86e364da49e9c8766a0e10439ee47");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream is = connection.getInputStream();
            return is;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}