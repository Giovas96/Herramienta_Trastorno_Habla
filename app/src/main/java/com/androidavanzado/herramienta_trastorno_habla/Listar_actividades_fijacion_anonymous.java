package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class Listar_actividades_fijacion_anonymous extends AppCompatActivity {

    private ListView actividades;
    ImageView back;
    String opciones []={"La rana (repetición de fonema)"};
    int iconos [] = {R.drawable.rana};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_fijacion_anonymous);

        back = findViewById(R.id.back_list_actividad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Listar_actividades_fijacion_anonymous.this, Listar_actividades_anonymous.class);
                startActivity(b);
            }
        });

        actividades= findViewById(R.id.menu_fijacion_a);
        MyAdapter adapter= new MyAdapter(Listar_actividades_fijacion_anonymous.this, R.layout.list_item, opciones, iconos);
        actividades.setAdapter(adapter);
        actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //juego de rana
                    Intent intent= new Intent(Listar_actividades_fijacion_anonymous.this, Opciones_rana.class);
                    startActivity(intent);

                }
            }
        });

    }
}
