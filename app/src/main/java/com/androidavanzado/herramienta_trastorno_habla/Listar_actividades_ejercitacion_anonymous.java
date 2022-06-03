package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class Listar_actividades_ejercitacion_anonymous extends AppCompatActivity {
    private ListView actividades;
    ImageView back;
    String opciones []={"Memorama (Ejercitación de fonema)"};
    int iconos [] = {R.drawable.memoria};
    Dialog dialog;
    Button btn_op_memorama, btn_cargar_material_memorama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_ejercitacion_anonymous);

        dialog = new Dialog(Listar_actividades_ejercitacion_anonymous.this);
        dialog.setContentView(R.layout.dialogo_memorama);

        back = findViewById(R.id.back_list_actividad);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent b = new Intent(Listar_actividades_ejercitacion_anonymous.this, Listar_actividades_anonymous.class);
                startActivity(b);
            }
        });

        actividades= findViewById(R.id.menu_ejercitacion_a);
        MyAdapter adapter= new MyAdapter(Listar_actividades_ejercitacion_anonymous.this, R.layout.list_item, opciones, iconos);
        actividades.setAdapter(adapter);
        actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //juego de rana
                    btn_op_memorama = dialog.findViewById(R.id.btn_Jugar_op_memorama);
                    btn_cargar_material_memorama = dialog.findViewById(R.id.btn_Cargar_material_memorama);

                    btn_op_memorama.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent= new Intent(Listar_actividades_ejercitacion_anonymous.this, Opciones_memorama.class);
                            startActivity(intent);

                        }
                    });

                    btn_cargar_material_memorama.setVisibility(View.GONE);
                    /*btn_cargar_material_memorama.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent= new Intent(Listar_actividades_ejercitacion_anonymous.this, Load.class);
                            startActivity(intent);
                        }
                    });*/

                    dialog.show();

                }



            }
        });


    }
}