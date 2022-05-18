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

import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Habitos;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_HistorialE;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_HistorialE extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button antecedentesm, datosg;
    FirebaseFirestore nFirestore;
    TextView guarderia, separacion, kinder, primaria, opinion, conducta, cambios, rendimiento;
    ImageView back, edit;
    DocumentReference historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__historial_e);
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        historial = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("HistoriaEscolar");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_HistorialE.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_HistorialE.this, Editar_HistorialE.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });

        historial.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String guar = document.getString("Asistió a guardería");
                        String separ = document.getString("Presentó angustia por separación");
                        String kind = document.getString("Edad en la que inició el kinder");
                        String prim = document.getString("Edad en la que inició la primaria");
                        String opin = document.getString("Opinión de la escuela acerca del niño");
                        String conduc = document.getString("Cómo es su conducta escolar");
                        String camb = document.getString("Cambios de escuela (motivos)");
                        String rendi = document.getString("Rendimiento académico");

                        SetearDatos(guar,separ,kind,prim,opin,conduc,camb,rendi);

                        antecedentesm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Consultar_HistorialE.this, Consultar_AntecedentesM.class);
                                i.putExtra("idpa", idpaciente);
                                startActivity(i);
                            }
                        });
                        datosg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j = new Intent(Consultar_HistorialE.this, Consultar_DatosG.class);
                                j.putExtra("idpa", idpaciente);
                                startActivity(j);
                            }
                        });
                    } else {
                        Toast.makeText(Consultar_HistorialE.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Consultar_HistorialE.this, HistoriaEscolar.class));
                    }
                } else {
                    Toast.makeText(Consultar_HistorialE.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        antecedentesm=findViewById(R.id.butAntHE_c);
        datosg=findViewById(R.id.butDatogHE_c);
        guarderia= findViewById(R.id.editTextHEguarderiar_c);
        separacion = findViewById(R.id.editTextHEangustia_c);
        kinder= findViewById(R.id.editTextHEkinder_c);
        primaria= findViewById(R.id.editTextHEprimaria_c);
        opinion= findViewById(R.id.editTextTextHEopinionesc_c);
        conducta= findViewById(R.id.editTextHEconducta_c);
        cambios= findViewById(R.id.editTextHEcambiosesc_c);
        rendimiento= findViewById(R.id.editTextHErendimiento_c);
    }

    private void SetearDatos(String guar,String sepa,String kin,String pri,String opi,String cond,String cam,String rend){
        guarderia.setText(guar);
        separacion.setText(sepa);
        kinder.setText(kin);
        primaria.setText(pri);
        opinion.setText(opi);
        conducta.setText(cond);
        cambios.setText(cam);
        rendimiento.setText(rend);
    }
}