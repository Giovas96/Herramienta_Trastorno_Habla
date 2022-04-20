package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

        menu_paciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en Datos
                   // startActivity(new Intent(Paciente.this, Paciente.class));
                } else if (position ==1){
                    //click en Historial clinico
                    startActivity(new Intent(Paciente.this, Historialclinico.class));
                } else if (position ==2){
                    //Click en Evaluación

                } else if (position ==3){
                    //Click en Citas

                }
            }
        });
    }
}