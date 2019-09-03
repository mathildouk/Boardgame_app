package com.example.boardgame;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import com.example.boardgame.dao.JeuAAcheterDAOfactory;
import com.example.boardgame.dao.JeuAJouerDAOfactory;
import com.example.boardgame.dao.JeuJoueDAOfactory;
import com.example.boardgame.dao.JeuPossedeDAOfactory;
import com.example.boardgame.dao.PartieDAOfactory;
import com.example.boardgame.dao.sqlite.MyOpenHelper;
import com.example.boardgame.metier.Partie;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button jeux, historique;
    TextView nbjeuxpossedés,nbjeuxveutpossedés,nbjeuxjoués, nbjeuxveutjoués, statParties;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolbar();

        new MyOpenHelper(this).getWritableDatabase().close();

        jeux = findViewById(R.id.Jeux);
        jeux.setOnClickListener(this);

        historique = findViewById(R.id.Historique);
        historique.setOnClickListener(this);

        nbjeuxpossedés = findViewById(R.id.nbjeuxpossedés);
        nbjeuxveutpossedés = findViewById(R.id.nbjeuxveutpossedés);
        nbjeuxjoués = findViewById(R.id.nbjeuxjoués);
        nbjeuxveutjoués = findViewById(R.id.nbjeuxveutjoues);
        statParties = findViewById(R.id.statparties);



        StatistiquesJeux();
        StatistiquesParties();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_refresh, menu);

        return true;
    }

    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar
        toolbar.setTitle("Menu principal");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.menu_activity_main_refresh:
                StatistiquesJeux();
                StatistiquesParties();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick (View v){

        switch (v.getId()){
            case R.id.Jeux :
                Toast.makeText(this, "Vos Jeux", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(getBaseContext(), ListeJeuxPossedes.class);
                startActivityForResult(myIntent, 0);
                break;

            case R.id.Historique:
                Toast.makeText(this, "Votre Historique", Toast.LENGTH_LONG).show();
                Intent Intent2 = new Intent(getBaseContext(), Historique.class);
                startActivityForResult(Intent2, 0);
                break;
        }

    }

    public  void StatistiquesParties(){
        List<Partie> parties = PartieDAOfactory.getPartieDAO(this).chargerParties();
        Collections.sort(parties);


        int nbPartie = parties.size();
        int nbVictoire = PartieDAOfactory.getPartieDAO(this).getNbvictoire();
        int nbVictoireE = PartieDAOfactory.getPartieDAO(this).getNbvictoireE();
        int nbDefaites = PartieDAOfactory.getPartieDAO(this).getNbdefaite();
        int nbDefaitesH = PartieDAOfactory.getPartieDAO(this).getNbdefaiteH();
        int nbEgalites = PartieDAOfactory.getPartieDAO(this).getNbegalite();

        String stats = "";


        if (nbPartie != 0){
            stats ="Nombre de parties jouées : "+nbPartie;
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String dateFormat = simpleDateFormat.format(parties.get(0).getDate());
            stats = stats + "\nDernière partie jouée le : "+dateFormat;
            if (nbVictoire!=0){
                stats=stats+"\n"+
                        "Nombre de victoires : "+nbVictoire+" ("+nbVictoire*100/nbPartie+"%)";
                if(nbVictoireE!=0){
                    stats=stats+"\n dont "+nbVictoireE+" victoires écrasantes";
            }
            }
            else{
                stats=stats+"\n"+"Aucune victoire";

            }
            if (nbEgalites!=0){
                stats=stats+"\n"+
                        "Nombre d'égalités : "+nbEgalites+" ("+nbEgalites*100/nbPartie+"%)";
            }
            else{
                stats=stats+"\n"+"Aucune égalité";
            }

            if (nbDefaites!=0){
                stats=stats+"\n"+
                        "Nombre de défaites : "+nbVictoire+" ("+nbVictoire*100/nbPartie+"%)"+"\n";
                if(nbDefaitesH!=0){
                    stats=stats+"\n dont "+nbDefaitesH+" victoires humiliantes ..";
                }
            }
            else{
                stats=stats+"\n"+"Aucune défaite";
            }


        }
        else {
            stats = "Aucune partie joué";
        }

        statParties.setText(stats);

    }

    public void StatistiquesJeux(){

        Integer nbpossédés = JeuPossedeDAOfactory.getJeuPossedeDAO(this).nbJeux();
        String messagepossédés;
        if(nbpossédés == 0){
            messagepossédés= "Aucun jeu possédé";
        }
        else if(nbpossédés == 1){
            messagepossédés = "1 jeu possédé";
        }
        else{
            messagepossédés= nbpossédés.toString()+" jeux possédés";
        }
        nbjeuxpossedés.setText(messagepossédés);

        Integer nbveutpossédés = JeuAAcheterDAOfactory.getJeuAAcheterDAO(this).nbJeux();
        String messageveutpossédés;
        if(nbveutpossédés == 0){
            messageveutpossédés= "Aucun jeu à acheter";
        }
        else if(nbveutpossédés == 1){
            messageveutpossédés = "1 jeu à acheter";
        }
        else{
            messageveutpossédés= nbveutpossédés.toString()+" jeux à acheter";
        }

        nbjeuxveutpossedés.setText(messageveutpossédés);



        Integer nbjoués = JeuJoueDAOfactory.getJeuJoueDAO(this).nbJeux();
        String messagejoués;
        if(nbjoués == 0){
            messagejoués= "Aucun jeu joué";
        }
        else if(nbjoués == 1){
            messagejoués = "1 jeu déjà joué";
        }
        else{
            messagejoués= nbjoués.toString()+" jeux déjà joués";
        }

        nbjeuxjoués.setText(messagejoués);



        Integer nbveutjoués = JeuAJouerDAOfactory.getJeuAJouerDAO(this).nbJeux();
        String messageveutjoués;
        if(nbveutjoués == 0){
            messageveutjoués= "Aucun jeu à jouer";
        }
        else if(nbveutjoués == 1){
            messageveutjoués = "1 jeu à jouer";
        }
        else{
            messageveutjoués= nbveutjoués.toString()+" jeux à jouer";
        }

        nbjeuxveutjoués.setText(messageveutjoués);
    }

    //   @Override
 //   public boolean onOptionsItemSelected(MenuItem item) {
    //       //3 - Handle actions on menu items
    //      switch (item.getItemId()) {
    //         case R.id.menu_activity_main_params:
    //            Toast.makeText(this, "Il n'y a rien à paramétrer ici, passez votre chemin...", Toast.LENGTH_LONG).show();
    //           return true;
    //         case R.id.menu_activity_main_search:
    //            Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
    //             return true;
    //         default:
    //              return super.onOptionsItemSelected(item);
    //     }
  //  }


}

/*Thread thread =new Thread(new Runnable() {
    @Override
    public void run() {
        Log.i("thread","hello");
        //code
    }
});
thread.start();

//code

*/

/* runOnUiThread(new Runnable(){
    //code à mettre ds le thread principal
});*/