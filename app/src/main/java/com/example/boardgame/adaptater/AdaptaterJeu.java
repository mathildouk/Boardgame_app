package com.example.boardgame.adaptater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.boardgame.R;
import com.example.boardgame.metier.Jeu;

import java.util.List;

public class AdaptaterJeu extends BaseAdapter{

    private List<Jeu> jeux;
    private Context context;

    public AdaptaterJeu(Context context, List<Jeu> jeux){
        this.context=context;
        this.jeux=jeux;

    }
    public int getCount(){
        return jeux.size();
    }

    public Object getItem(int position){
        return jeux.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.elements_liste_jeux,parent,false);
        TextView nom= v.findViewById(R.id.nomJeu);
        nom.setText(jeux.get(position).getNom());
        //id.setText(jeux.get(position).getIdentifiant());

        return v;
    }
}