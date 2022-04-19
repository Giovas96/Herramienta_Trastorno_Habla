package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SesionTerapeuta extends AppCompatActivity {

    private ListView menu_terapeuta;
    String opciones []={"Listar Pacientes", "Listar Actividades", "Calendario", "Información" };
    int iconos [] = {R.drawable.pacientes,R.drawable.juegos, R.drawable.calendario, R.drawable.info};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion_terapeuta);

        menu_terapeuta= findViewById(R.id.menu_terapeuta);


        //ArrayAdapter <String> adapter= new ArrayAdapter<String>(SesionTerapeuta.this, android.R.layout.simple_list_item_1, opciones);
        MyAdapter adapter= new MyAdapter(SesionTerapeuta.this, R.layout.list_item, opciones, iconos);
        menu_terapeuta.setAdapter(adapter);

    }
}