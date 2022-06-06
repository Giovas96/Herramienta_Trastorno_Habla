package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Listar_actividades extends AppCompatActivity {
    private ListView actividades;
    ImageView back, edit;
    String opciones []={"Discriminación Auditiva", "Obtención del fonema", "Fijación de fonema", "Ejercitación", "Integración" };
    int iconos [] = {R.drawable.escucha,R.drawable.discurso,R.drawable.rana, R.drawable.memoria, R.drawable.galeria};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades);
        back = findViewById(R.id.back_list_actividad);
        edit=findViewById(R.id.edit_palabras);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Listar_actividades.this, SesionTerapeuta.class);
                startActivity(b);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Listar_actividades.this, Opciones_editar_palabras.class);
                startActivity(b);
            }
        });

        actividades= findViewById(R.id.menu_actividades);
        MyAdapter adapter= new MyAdapter(Listar_actividades.this, R.layout.list_item, opciones, iconos);
        actividades.setAdapter(adapter);
        actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en discriminacion auditiva
                    Toast.makeText(Listar_actividades.this, "Discriminación auditiva", Toast.LENGTH_SHORT).show();
                } else if (position ==1){
                    //click en obtención del fonema
                    Toast.makeText(Listar_actividades.this, "Obtención de fonema", Toast.LENGTH_SHORT).show();

                } else if (position ==2){
                    //click en fijación de fonema
                    Intent intent= new Intent(Listar_actividades.this, Listar_actividades_fijacion.class);
                    startActivity(intent);

                } else if (position ==3){
                    //Click en Ejercitación
                    Intent intent= new Intent(Listar_actividades.this, Listar_actividades_ejercitacion.class);
                    startActivity(intent);

                } else if (position == 4){
                    //Click en Integración
                    Intent intent= new Intent(Listar_actividades.this, Opciones_Galeria.class);
                    startActivity(intent);
                }
            }
        });
    }
}