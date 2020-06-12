package com.example.tfgapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;


public class MyListAdapter extends BaseAdapter {
    private final Context context;
    private final ArrayList<String> title;
    private final ArrayList<String> description;
    //private final Integer image;

    public MyListAdapter(Activity context, ArrayList<String> titulos,ArrayList<String> descripcion) {
        this.context = context;
        this.title = titulos;
        this.description=descripcion;
        //this.image = image;

    }

    @Override
    public int getCount() {
        return this.title.size();
    }

    @Override
    public Object getItem(int position) {
        return this.title.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    public View getView(int position, View view, ViewGroup parent) {
        // Copiamos la vista
        View v = view;

        //Inflamos la vista con nuestro propio layout
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        v = layoutInflater.inflate(R.layout.listcursos_row, null);
        // Valor actual según la posición

        String currentTitle = title.get(position);
        String currentDescription = description.get(position);

        // Referenciamos el elemento a modificar y lo rellenamos
        TextView textViewTitle = (TextView) v.findViewById(R.id.txt_title);
        TextView textViewDesciption = (TextView) v.findViewById(R.id.txt_description);
        textViewTitle.setText(currentTitle);
        textViewDesciption.setText(currentDescription);

        //Devolvemos la vista inflada
        return v;
    }
}
