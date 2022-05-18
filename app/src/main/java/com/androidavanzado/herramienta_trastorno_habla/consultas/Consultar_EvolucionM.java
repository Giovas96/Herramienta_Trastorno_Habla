package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Evolucionmotriz;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_EvolucionM extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button lenguaje, habitos;
    FirebaseFirestore nFirestore;
    TextView cabeza, sentar, parar, gateo, edad, tiempo, caminar, chocar, diur,noctur, mano;
    ImageView back, edit;
    DocumentReference evolucion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__evolucion_m);
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        evolucion = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("EvolucionMotriz");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_EvolucionM.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_EvolucionM.this, Editar_EvolucionM.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });

        evolucion.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String cab = document.getString("A qué edad sostuvo la cabeza");
                        String sent = document.getString("Se sentó sin apoyo");
                        String par = document.getString("Se paró por si mismo");
                        String gat = document.getString("Gateó");
                        String eda = document.getString("a qué edad");
                        String tiem = document.getString("Durante cuánto tiempo");
                        String cam = document.getString("Caminó");
                        String obj = document.getString("Chocaba con los objetos");
                        String diu = document.getString("Diurno");
                        String noc = document.getString("Nocturno");
                        String pref = document.getString("Mano y pie de preferencia");

                        SetearDatos(cab,sent,par,gat,eda,tiem,cam,obj,diu,noc,pref);

                        lenguaje.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Consultar_EvolucionM.this, Consultar_Lenguaje.class);
                                i.putExtra("idpa", idpaciente);
                                startActivity(i);
                            }
                        });

                        habitos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j = new Intent(Consultar_EvolucionM.this, Consultar_Habitos.class);
                                j.putExtra("idpa", idpaciente);
                                startActivity(j);
                            }
                        });


                    } else {
                        Toast.makeText(Consultar_EvolucionM.this,"El documento no existe", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Consultar_EvolucionM.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        lenguaje=findViewById(R.id.butLengEM_c);
        habitos=findViewById(R.id.butHabiEM_c);
        cabeza=findViewById(R.id.editTextEMcabeza_c);
        sentar=findViewById(R.id.editTextEMsento_c);
        parar=findViewById(R.id.editTextEMparo_c);
        gateo=findViewById(R.id.editTextEMgateo_c);
        edad =findViewById(R.id.editTextEMgateoedad_c);
        tiempo=findViewById(R.id.editTextEMtiempogateo_c);
        caminar=findViewById(R.id.editTextEMcamino_c);
        chocar=findViewById(R.id.editTextEMchocar_c);
        diur=findViewById(R.id.editTextEMdiurno_c);
        noctur=findViewById(R.id.editTextEMnocturno_c);
        mano=findViewById(R.id.editTextEMmanopieref_c);

    }
    private void SetearDatos(String cab,String senr,String par,String gat,String ed,String tiem,String cami,String cho,String di,String noctu,String man){

        cabeza.setText(cab);
        sentar.setText(senr);
        parar.setText(par);
        gateo.setText(gat);
        edad.setText(ed);
        tiempo.setText(tiem);
        caminar.setText(cami);
        chocar.setText(cho);
        diur.setText(di);
        noctur.setText(noctu);
        mano.setText(man);

    }
}