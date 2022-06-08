package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class Listar_actividades_fijacion extends AppCompatActivity {

    private ListView actividades;
    ImageView back, edit;
    String opciones []={"La rana (repetición de fonema)"};
    int iconos [] = {R.drawable.rana};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_fijacion);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        back = findViewById(R.id.back_list_actividad);
        edit=findViewById(R.id.edit_palabras);
        edit.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Listar_actividades_fijacion.this, Listar_actividades.class);
                startActivity(b);
            }
        });

        actividades= findViewById(R.id.menu_fijacion);
        MyAdapter adapter= new MyAdapter(Listar_actividades_fijacion.this, R.layout.list_item, opciones, iconos);
        actividades.setAdapter(adapter);
        actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //juego de rana
                    Intent intent= new Intent(Listar_actividades_fijacion.this, Opciones_rana.class);
                    startActivity(intent);

                }
            }
        });

    }
}