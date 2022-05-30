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
    ImageView back;
    String opciones []={"Obtención del fonema", "Fijación de fonema", "Ejercitación", "Integración" };
    int iconos [] = {R.drawable.discurso,R.drawable.rana, R.drawable.memoria, R.drawable.galeria};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades);
        back = findViewById(R.id.back_list_actividad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Listar_actividades.this, SesionTerapeuta.class);
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
                    //click en obtención de fonema
                    Toast.makeText(Listar_actividades.this, "Obtención de fonema", Toast.LENGTH_SHORT).show();
                } else if (position ==1){
                    //click en fijación de fonema
                    Toast.makeText(Listar_actividades.this, "Fijación de fonema", Toast.LENGTH_SHORT).show();

                } else if (position ==2){
                    //Click en Ejercitación
                    Toast.makeText(Listar_actividades.this, "Ejercitación", Toast.LENGTH_SHORT).show();


                } else if (position ==3){
                    //Click en Integración
                    Toast.makeText(Listar_actividades.this, "Integración", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}