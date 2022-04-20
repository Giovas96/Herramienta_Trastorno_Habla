package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class Historialclinico extends AppCompatActivity {

    private ListView menu_historial;
    String opciones []={"Datos generales", "Desarrollo", "Hábitos", "Evolución motriz", "Lenguaje", "Antecedentes médicos", "Historia escolar" };
    int iconos [] = {R.drawable.datosgeneralesh,R.drawable.desarrolloh, R.drawable.habitosh, R.drawable.motrizh, R.drawable.lenguajeh, R.drawable.antecedenteh, R.drawable.escolarh};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historialclinico);

        menu_historial= findViewById(R.id.menu_historial);

        //ArrayAdapter <String> adapter= new ArrayAdapter<String>(SesionTerapeuta.this, android.R.layout.simple_list_item_1, opciones);
        MyAdapter adapter= new MyAdapter(Historialclinico.this, R.layout.list_item, opciones, iconos);
        menu_historial.setAdapter(adapter);

        menu_historial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en Datos generales
                    startActivity(new Intent(Historialclinico.this, DatosGenerales.class));
                } else if (position ==1){
                    //click en Desarrollo
                    startActivity(new Intent(Historialclinico.this, Desarrollo.class));
                } else if (position ==2){
                    //Click en Habitos
                    startActivity(new Intent(Historialclinico.this, Habitos.class));
                } else if (position ==3){
                    //Click en Evolucion motriz
                    startActivity(new Intent(Historialclinico.this, Evolucionmotriz.class));
                }else if (position ==4){
                    //Click en Evolucion motriz
                    startActivity(new Intent(Historialclinico.this, Lenguaje.class));
                }else if (position ==5){
                    //Click en Evolucion motriz
                    startActivity(new Intent(Historialclinico.this, Antecedentemedicos.class));
                }else if (position ==6){
                    //Click en Evolucion motriz
                    startActivity(new Intent(Historialclinico.this, HistoriaEscolar.class));
                }
            }
        });

    }
}