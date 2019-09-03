package com.example.boardgame.metier;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "jeu")
public class Jeu implements Comparable{

    @DatabaseField(id = true)
    private Integer identifiant;
    @DatabaseField
    private String nom;
    @DatabaseField
    private String yearpublished;
    @DatabaseField
    private String minplayers;
    @DatabaseField
    private String maxplayers;
    @DatabaseField
    private String playingtime;
    @DatabaseField
    private String minplaytime;
    @DatabaseField
    private String maxplaytime;
    @DatabaseField
    private String age;
    @DatabaseField
    private String description;
    @DatabaseField
    private String thumbnail;
    @DatabaseField
    private String image;


    public Jeu() {
    }

    public Jeu(Integer id, String nom) {

        this.nom = nom;
        this.identifiant= id;
    }

    public Integer getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(Integer identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }

    public String getYearpublished() {
        return yearpublished;
    }

    public void setYearpublished(String yearpublished) {
        this.yearpublished = yearpublished;
    }

    public String getMinplayers() {
        return minplayers;
    }

    public void setMinplayers(String minplayers) {
        this.minplayers = minplayers;
    }

    public String getMaxplayers() {
        return maxplayers;
    }

    public void setMaxplayers(String maxplayers) {
        this.maxplayers = maxplayers;
    }

    public String getPlayingtime() {
        return playingtime;
    }

    public void setPlayingtime(String playingtime) {
        this.playingtime = playingtime;
    }

    public String getMinplaytime() {
        return minplaytime;
    }

    public void setMinplaytime(String minplaytime) {
        this.minplaytime = minplaytime;
    }

    public String getMaxplaytime() {
        return maxplaytime;
    }

    public void setMaxplaytime(String maxplaytime) {
        this.maxplaytime = maxplaytime;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int compareTo(Object o) {
        Jeu j=(Jeu)o;
        return getNom().compareTo(j.getNom());
    }
}