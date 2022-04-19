package com.androidavanzado.herramienta_trastorno_habla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        private String listOpciones[];
        private int listIcons[];

        public MyAdapter(Context context, int layout, String [] opciones, int [] iconos){
            this.context = context;
            this.layout = layout;
            this.listOpciones = opciones;
            this.listIcons = iconos;


        }

    @Override
    public int getCount() {
        return listOpciones.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = view;

        //inflamos la vista con nuestro propio layout

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v=layoutInflater.inflate(R.layout.list_item, null);

        //valor actual de la posicion

        TextView textoelemento= v.findViewById(R.id.txt_opcion);
        ImageView iconoelemento= v.findViewById(R.id.iconmenu);
        textoelemento.setText(listOpciones[position]);
        iconoelemento.setImageResource(listIcons[position]);


        return v;


    }
}
