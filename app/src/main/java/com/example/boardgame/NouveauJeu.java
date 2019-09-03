package com.example.boardgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.boardgame.R;
import com.example.boardgame.metier.Jeu;
import com.example.boardgame.metier.boardgames;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NouveauJeu extends Activity {

    TextView infos;
    XmlMapper xmlMapper;
    InputStream api;
    boardgames boardgames;
    Jeu jeu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nouveau_jeu);


        Integer idJeu = getIntent().getExtras().getInt("id");
        String nomJeu = getIntent().getExtras().getString("nom");

        jeu = new Jeu(idJeu,nomJeu);

        infos = findViewById(R.id.descriptionJeu);
        infos.setText("id : "+jeu.getIdentifiant().toString()+"\n nom : "+jeu.getNom());

        try {
            jeu = RechercheInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        infos.setText("id : "+jeu.getIdentifiant().toString()+"\n nom : "+jeu.getNom()+"\n age : "+jeu.getAge().toString());

    }

    public Jeu RechercheInfo() throws IOException {


        //Thread
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Integer id = jeu.getIdentifiant();
                // StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build()); //à ne pas faire en vrai
                URL url = null;
                try {
                    url = new URL("https://www.boardgamegeek.com/xmlapi/boardgame/"+jeu.getIdentifiant().toString()+"&apikey=79c327454a97a8faa33d7b06f3d86e364da49e9c8766a0e10439ee47");
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
}
