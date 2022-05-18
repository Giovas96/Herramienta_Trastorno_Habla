package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_paciente;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_tutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_tutor extends AppCompatActivity {
    ImageView back, edit;
    TextView Nombre, Apellidopat, Apellidomat, Fechanac, celular, casa, profesion,
            Nombred, Apellidopatd, Apellidomatd, Fechanacd, celulard, casad, profesiond ;
    DocumentReference tutor,tutord;
    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_tutor);
        idpaciente= getIntent().getStringExtra("idp");
        Inicializarpaciente();
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        tutor = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("tutorp");
        tutord= terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("tutorpd");

         tutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String nom= document.getString("Nombre()s");
                        String app= document.getString("Apellido Paterno");
                        String apm= document.getString("Apellido Materno");
                        String nac= document.getString("Fecha de nacimiento");
                        String celu= document.getString("Celular");
                        String home= document.getString("Casa");
                        String pro= document.getString("Profesión");

                        SetearDatos(nom,app,apm,nac,celu,home,pro);
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

         tutord.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
             @Override
             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                 if (task.isSuccessful()) {
                     DocumentSnapshot document = task.getResult();
                     if (document.exists()) {

                         String nomd= document.getString("Nombre()s");
                         String appd= document.getString("Apellido Paterno");
                         String apmd= document.getString("Apellido Materno");
                         String nacd= document.getString("Fecha de nacimiento");
                         String celud= document.getString("Celular");
                         String homed= document.getString("Casa");
                         String prod= document.getString("Profesión");

                         SetearDatosd(nomd,appd,apmd,nacd,celud,homed,prod);
                     } else {
                         //Log.d(TAG, "No such document");
                     }
                 } else {
                     //Log.d(TAG, "get failed with ", task.getException());
                 }
             }
         });

       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_tutor.this, consultar_datos_paciente.class);
                i.putExtra("idp",idpaciente);
                startActivity(i);
            }
        });

       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent j = new Intent(Consultar_tutor.this, Editar_tutor.class);
               j.putExtra("idp",idpaciente);
               startActivity(j);
           }
       });

    }

    private void Inicializarpaciente(){

        back=findViewById(R.id.back_consultut);
        edit = findViewById(R.id.edit_tut);
        Nombre  = findViewById(R.id.editTextnombretut_c);
        Apellidopat = findViewById(R.id.editTextapellidoptup_c);
        Apellidomat= findViewById(R.id.editTextapellidomtu_c);
        Fechanac= findViewById(R.id.editTextfechatut_c);
        celular= findViewById(R.id.editTextceltut_c);
        casa = findViewById(R.id.editTextcasatut_c);
        profesion= findViewById(R.id.editTextproftut_c);
        Nombred = findViewById(R.id.editTextnombtut2_c);
        Apellidopatd = findViewById(R.id.editTextapellidoptu2_c);
        Apellidomatd = findViewById(R.id.editTextapellidomtu2_c);
        Fechanacd = findViewById(R.id.editTextfechatut2_c);
        celulard = findViewById(R.id.editTextceltut2_c);
        casad = findViewById(R.id.editTextcasatut2_c);
        profesiond = findViewById(R.id.editTextproftut2_c);

    }

    private void SetearDatos(String nombre,String apellidopat,String apellidomat,String fechanac,String cel,String cas,String prof){

        Nombre.setText(nombre);
        Apellidopat.setText(apellidopat);
        Apellidomat.setText(apellidomat);
        Fechanac.setText(fechanac);
        celular.setText(cel);
        casa.setText(cas);
        profesion.setText(prof);
    }

    private void SetearDatosd(String nombre,String apellidopat,String apellidomat,String fechanac,String cel,String cas,String prof){

        Nombred.setText(nombre);
        Apellidopatd.setText(apellidopat);
        Apellidomatd.setText(apellidomat);
        Fechanacd.setText(fechanac);
        celulard.setText(cel);
        casad.setText(cas);
        profesiond.setText(prof);
    }
}