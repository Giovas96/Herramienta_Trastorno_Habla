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

import com.androidavanzado.herramienta_trastorno_habla.DatosGenerales;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_DatosG;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_DatosG extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button desarrollo, historiae;
    FirebaseFirestore nFirestore;
    TextView fecha, entrevistado, relacion, referido, paciente, nacimiento, fechanac;
    ImageView back, edit;
    DocumentReference cdatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__datos_g);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        cdatos = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente)
                .collection("datos").document("DatosGenerales");


        cdatos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String fe = document.getString("Fecha");
                        String entre = document.getString("Nombre del entrevistado");
                        String rela = document.getString("Relacion con el paciente");
                        String refe = document.getString("Referido por");
                        String paci = document.getString("Nombre del paciente");
                        String luga = document.getString("Lugar de nacimiento");
                        String fecnaci = document.getString("Fecha de nacimiento");


                        SetearDatos(fe,entre,rela,refe,paci,luga,fecnaci);

                        desarrollo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Consultar_DatosG.this, Consultar_Desarrollo.class);
                                i.putExtra("idpa", idpaciente);
                                startActivity(i);
                            }
                        });

                        historiae.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j = new Intent(Consultar_DatosG.this, Consultar_HistorialE.class);
                                j.putExtra("idpa", idpaciente);
                                startActivity(j);
                            }
                        });
                    } else {
                        Toast.makeText(Consultar_DatosG.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Consultar_DatosG.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_DatosG.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_DatosG.this, Editar_DatosG.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        desarrollo=findViewById(R.id.butDesaDG_c);
        historiae=findViewById(R.id.butHistorDG_c);
        fecha = findViewById(R.id.editTextDGfecha_c);
        entrevistado=findViewById(R.id.editTextDGentrevistado_c);
        relacion= findViewById(R.id.editTextDGrelacion_c);
        referido= findViewById(R.id.editTextDGreferido_c);
        paciente =findViewById(R.id.editTextDGpaciente_c);
        nacimiento=findViewById(R.id.editTextDGnacimiento_c);
        fechanac= findViewById(R.id.editTextDGfechanac_c);
    }

    private void SetearDatos(String fec, String entr,String rel,String ref,String pac,String nac,String fecnac){

        fecha.setText(fec);
        entrevistado.setText(entr);
        relacion.setText(rel);
        referido.setText(ref);
        paciente.setText(pac);
        nacimiento.setText(nac);
        fechanac.setText(fecnac);
    }
}