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

import com.google.firebase.auth.FirebaseAuth;

public class Listar_actividades_anonymous extends AppCompatActivity {
    FirebaseAuth mAuth;
    private ListView actividades;
    ImageView back;
    String opciones []={"Discriminación Auditiva", "Obtención del fonema", "Fijación de fonema", "Ejercitación", "Integración" };
    int iconos [] = {R.drawable.escucha,R.drawable.discurso,R.drawable.rana, R.drawable.memoria, R.drawable.galeria};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_actividades_anonymous);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        back = findViewById(R.id.exit_list_actividad);
        mAuth=FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(Listar_actividades_anonymous.this, "Ha cerrado sesión exitosamente", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Listar_actividades_anonymous.this, MainActivity.class));
            }
        });

        actividades= findViewById(R.id.menu_actividades);
        MyAdapter adapter= new MyAdapter(Listar_actividades_anonymous.this, R.layout.list_item, opciones, iconos);
        actividades.setAdapter(adapter);
        actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en discriminacion auditiva
                    Toast.makeText(Listar_actividades_anonymous.this, "Discriminación auditiva", Toast.LENGTH_SHORT).show();
                } else if (position ==1){
                    //click en obtención del fonema
                    int i=1;
                    Intent intent= new Intent(Listar_actividades_anonymous.this, Seleccion_obtencion_fonema.class);
                    intent.putExtra("anoni",i);
                    startActivity(intent);

                } else if (position ==2){
                    //click en fijación de fonema
                    Intent intent= new Intent(Listar_actividades_anonymous.this, Listar_actividades_fijacion_anonymous.class);
                    startActivity(intent);

                } else if (position ==3){
                    //Click en Ejercitación
                    Intent intent= new Intent(Listar_actividades_anonymous.this, Listar_actividades_ejercitacion_anonymous.class);
                    startActivity(intent);

                } else if (position == 4){
                    //Click en Integración
                    Intent intent= new Intent(Listar_actividades_anonymous.this, Opciones_Galeria_Anonymous.class);
                    startActivity(intent);
                }
            }
        });
    }
}