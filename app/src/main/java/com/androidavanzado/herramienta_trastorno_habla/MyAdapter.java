package com.androidavanzado.herramienta_trastorno_habla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

        private Context context;
        LayoutInflater inflater;
        private String listOpciones[];
        private int listIcons[];

        public MyAdapter(Context context, int layout, String [] opciones, int [] iconos){
            this.context = context;
            this.listOpciones = opciones;
            this.listIcons = iconos;
            inflater = LayoutInflater.from(context);


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
        view = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView textoelemento= view.findViewById(R.id.Datos_personales);
        ImageView iconoelemento= view.findViewById(R.id.imageIcon);
        textoelemento.setText(listOpciones[position]);
        iconoelemento.setImageResource(listIcons[position]);


        return view;


    }
}
