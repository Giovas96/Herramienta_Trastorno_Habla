package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class Paciente extends AppCompatActivity {

    private ListView menu_paciente;
    String opciones []={"Datos", "Historial Médico", "Evaluación", "Citas" };
    int iconos [] = {R.drawable.datospaciente,R.drawable.historialmedico, R.drawable.evaluacion, R.drawable.citas};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        menu_paciente= findViewById(R.id.menu_paciente);

        //ArrayAdapter <String> adapter= new ArrayAdapter<String>(SesionTerapeuta.this, android.R.layout.simple_list_item_1, opciones);
        MyAdapter adapter= new MyAdapter(Paciente.this, R.layout.list_item, opciones, iconos);
        menu_paciente.setAdapter(adapter);

    }
}