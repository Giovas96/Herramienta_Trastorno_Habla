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
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.consultas.consultar_datos_paciente;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Citas;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_paciente;

public class Paciente extends AppCompatActivity {

    private ListView menu_paciente;
    String opciones []={"Datos", "Historial Médico", "Evaluación", "Citas" };
    int iconos [] = {R.drawable.datospaciente,R.drawable.historialmedico, R.drawable.evaluacion, R.drawable.citas};
    String idpaciente;
    Dialog dialog;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        idpaciente= getIntent().getStringExtra("idpac");
        menu_paciente= findViewById(R.id.menu_paciente);
        dialog = new Dialog(Paciente.this);
        //ArrayAdapter <String> adapter= new ArrayAdapter<String>(SesionTerapeuta.this, android.R.layout.simple_list_item_1, opciones);
        MyAdapter adapter= new MyAdapter(Paciente.this, R.layout.list_item, opciones, iconos);
        menu_paciente.setAdapter(adapter);
        back= findViewById(R.id.back_pac);
        menu_paciente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    //click en Datos

                    //Declarar las vistas
                    Button bconsultar, beditar, beliminar;
                    //Realizar conexion con la vista
                    dialog.setContentView(R.layout.dialogo_datos_paciente);

                    //inicializar las vistas
                    bconsultar= dialog.findViewById(R.id.Consultar);
                    beditar= dialog.findViewById(R.id.Editar);
                    beliminar= dialog.findViewById(R.id.Eliminar);
                    beliminar.setVisibility(View.GONE);
                    bconsultar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i= new Intent (Paciente.this, consultar_datos_paciente.class );
                            i.putExtra("idp", idpaciente);
                            startActivity(i);
                            //Toast.makeText(Paciente.this, "Click en consultar", Toast.LENGTH_SHORT).show();
                        }
                    });

                    beditar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent j= new Intent (Paciente.this, Editar_paciente.class );
                            j.putExtra("idp", idpaciente);
                            startActivity(j);
                            //Toast.makeText(Paciente.this, "Click en editar", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.show();

                } else if (position ==1){
                    //click en Historial clinico
                    Intent i = new Intent(Paciente.this, Historialclinico.class);
                    i.putExtra("idpa", idpaciente);
                    startActivity(i);

                } else if (position ==2){
                    //Click en Evaluación
                    Intent c=new Intent(Paciente.this, Listarevaluacion.class);
                    c.putExtra("idpa",idpaciente);
                    startActivity(c);

                } else if (position ==3){
                    //Click en Citas
                    Intent c=new Intent(Paciente.this, Listarcitas.class);
                    c.putExtra("idpa",idpaciente);
                    startActivity(c);

                }
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent b= new Intent(Paciente.this, Listarpacientes.class);
              startActivity(b);
            }
        });

    }
}