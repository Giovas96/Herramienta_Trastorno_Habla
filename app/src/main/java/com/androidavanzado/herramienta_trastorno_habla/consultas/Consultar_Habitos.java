package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Habitos;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Habitos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_Habitos extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button desarrollo, evolucionm;
    FirebaseFirestore nFirestore;
    TextView leche, mamila, chupon, dedo, alimentacion, dormir, sueno;
    ImageView back, edit;
    DocumentReference chabitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__habitos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        chabitos = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("Habitos");


        chabitos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String lec = document.getString("Tomó leche materna ¿Cuánto tiempo?");
                        String mamil = document.getString("Tomó mamila ¿Cuánto tiempo?");
                        String chup = document.getString("Usó chupón");
                        String ded = document.getString("Se chupa el dedo");
                        String alimen = document.getString("Hubo problemas para alimentarlo");
                        String durm = document.getString("Edad en la que durmio toda la noche");
                        String suen = document.getString("¿Cómo fue su sueño en la noche?");

                        SetearDatos(lec,mamil,chup,ded,alimen,durm,suen);

                        desarrollo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Consultar_Habitos.this, Consultar_Desarrollo.class);
                                i.putExtra("idpa", idpaciente);
                                startActivity(i);
                            }
                        });

                        evolucionm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j = new Intent(Consultar_Habitos.this, Consultar_EvolucionM.class);
                                j.putExtra("idpa", idpaciente);
                                startActivity(j);
                            }
                        });
                    } else {
                        Toast.makeText(Consultar_Habitos.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Consultar_Habitos.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Habitos.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_Habitos.this, Editar_Habitos.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });


    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        evolucionm=findViewById(R.id.butEvolmH_c);
        desarrollo=findViewById(R.id.butDesaH_c);
        leche=findViewById(R.id.editTextHleche_c);
        mamila=findViewById(R.id.editTextHmamila_c);
        chupon=findViewById(R.id.editTextHchupon_c);
        dedo=findViewById(R.id.editTextHdedo_c);
        alimentacion =findViewById(R.id.editTextHalimentacion_c);
        dormir=findViewById(R.id.editTextHdormir_c);
        sueno=findViewById(R.id.editTextHsueno_c);
    }

    private void SetearDatos(String lec,String mam,String chu,String de,String ali,String dor,String sue){
        leche.setText(lec);
        mamila.setText(mam);
        chupon.setText(chu);
        dedo.setText(de);
        alimentacion.setText(ali);
        dormir.setText(dor);
        sueno.setText(sue);
    }
}