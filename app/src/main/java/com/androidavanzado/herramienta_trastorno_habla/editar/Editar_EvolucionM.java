package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Evolucionmotriz;
import com.androidavanzado.herramienta_trastorno_habla.Habitos;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.Lenguaje;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Editar_EvolucionM extends AppCompatActivity {

    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText cabeza, sentar, parar, gateo, edad, tiempo, caminar, chocar, diur,noctur, mano;
    ImageView back, save;
    DocumentReference document,evolucion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__evolucion_m);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        evolucion=document.collection("datos").document("EvolucionMotriz");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_EvolucionM.this, Historialclinico.class);
                i.putExtra("idpa",idpaciente);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
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

                    } else {
                        Toast.makeText(Editar_EvolucionM.this,"El documento no existe", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Editar_EvolucionM.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });


    }
    public void irHabitos(View view){
        Intent i = new Intent(Editar_EvolucionM.this, Editar_Habitos.class);
        i.putExtra("idpa",idpaciente);
        startActivity(i);
    }

    public void irLenguaje (View view){
        Intent j = new Intent(Editar_EvolucionM.this, Editar_Lenguaje.class);
        j.putExtra("idpa",idpaciente);
        startActivity(j);
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        cabeza=findViewById(R.id.editTextEMcabeza_e);
        sentar=findViewById(R.id.editTextEMsento_e);
        parar=findViewById(R.id.editTextEMparo_e);
        gateo=findViewById(R.id.editTextEMgateo_e);
        edad =findViewById(R.id.editTextEMgateoedad_e);
        tiempo=findViewById(R.id.editTextEMtiempogateo_e);
        caminar=findViewById(R.id.editTextEMcamino_e);
        chocar=findViewById(R.id.editTextEMchocar_e);
        diur=findViewById(R.id.editTextEMdiurno_e);
        noctur=findViewById(R.id.editTextEMnocturno_e);
        mano=findViewById(R.id.editTextEMmanopieref_e);

    }
    public void registrar(){
        String cab = cabeza.getText().toString();
        String sent= sentar.getText().toString();
        String par = parar.getText().toString();
        String gat= gateo.getText().toString();
        String eda = edad.getText().toString();
        String tiem= tiempo.getText().toString();
        String cam = caminar.getText().toString();
        String cho = chocar.getText().toString();
        String diu = diur.getText().toString();
        String noc = noctur.getText().toString();
        String man = mano.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("A qué edad sostuvo la cabeza", cab);
        map.put("Se sentó sin apoyo", sent);
        map.put("Se paró por si mismo", par);
        map.put("Gateó", gat);
        map.put("a qué edad", eda);
        map.put("Durante cuánto tiempo", tiem);
        map.put("Caminó", cam);
        map.put("Chocaba con los objetos", cho);
        map.put("Diurno", diu);
        map.put("Nocturno", noc);
        map.put("Mano y pie de preferencia", man);


        document.collection("datos").document("EvolucionMotriz").set(map);
        Toast.makeText(Editar_EvolucionM.this, "Registro de la evolución motriz exitoso ", Toast.LENGTH_SHORT).show();
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