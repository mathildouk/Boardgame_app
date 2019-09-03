package com.example.boardgame.metier;

import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "partie")
public class Partie implements Comparable{

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField (foreign= true)
    private Jeu jeu;
    @DatabaseField
    private Integer victoire=null;
    @DatabaseField
    private Integer score=null ;
    @DatabaseField
    private String remarque=null;
    @DatabaseField
    private Date date=null;
    @DatabaseField
    private Boolean VictoireEcrasante =null;
    @DatabaseField
    private Boolean DefaiteHumiliante =null;

    public Partie(){

    }
    public Partie(Jeu jeu) {
        this.jeu=jeu;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu =jeu;
    }

    public Integer getVictoire() {
        return victoire;
    }

    public void setVictoire(Integer victoire) {
        this.victoire = victoire;
    }

    public Boolean getVictoireEcrasante() {
        return VictoireEcrasante;
    }

    public void setVictoireEcrasante(Boolean victoireEcrasante) {
        VictoireEcrasante = victoireEcrasante;
    }

    public Boolean getDefaiteHumiliante() {
        return DefaiteHumiliante;
    }

    public void setDefaiteHumiliante(Boolean defaiteHumiliante) {
        DefaiteHumiliante = defaiteHumiliante;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemarque() {
        return remarque;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public Date getDate() {
       return date;
     }

   public void setDate(Date date) {
           this.date = date;
      }

    @Override
    public String toString() {
        String message = "Jeu : " + jeu.getNom();
        if(date != null){
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String dateFormat = simpleDateFormat.format(date);

            message = message +"\n Date : "+ dateFormat.toString();
        }
        if(victoire != null ){
            if(victoire == 1 ){
                message = message +"\n Victoire";
                if(VictoireEcrasante!=null){
                    if(VictoireEcrasante == true){
                        message = message +" Ecrasante !";
                    }
                }

            }
            else if (victoire == 2){
                message = message +"\n Défaite";
                if(DefaiteHumiliante!=null){
                    if(DefaiteHumiliante == true){
                        message = message +" humiliante ..";
                    }
                }

            }
            else if (victoire == 3){
                message = message +"\n Egalité";
            }

        }
        if(score != null){
            message = message + "\n Score : "+score.toString();
        }
        return message;

    }

    @Override
    public int compareTo(Object o) {
        Partie p=(Partie) o;
        return -getDate().compareTo(p.getDate());
    }
}
