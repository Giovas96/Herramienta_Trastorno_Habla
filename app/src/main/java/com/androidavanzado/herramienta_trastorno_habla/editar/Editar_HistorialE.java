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

import com.androidavanzado.herramienta_trastorno_habla.Antecedentemedicos;
import com.androidavanzado.herramienta_trastorno_habla.DatosGenerales;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Editar_HistorialE extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText guarderia, separacion, kinder, primaria, opinion, conducta, cambios, rendimiento;
    ImageView back, save;
    DocumentReference document,historial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__historial_e);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        document = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        historial = document.collection("datos").document("HistoriaEscolar");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_HistorialE.this, Historialclinico.class);
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

        historial.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String guar = document.getString("Asisti?? a guarder??a");
                        String separ = document.getString("Present?? angustia por separaci??n");
                        String kind = document.getString("Edad en la que inici?? el kinder");
                        String prim = document.getString("Edad en la que inici?? la primaria");
                        String opin = document.getString("Opini??n de la escuela acerca del ni??o");
                        String conduc = document.getString("C??mo es su conducta escolar");
                        String camb = document.getString("Cambios de escuela (motivos)");
                        String rendi = document.getString("Rendimiento acad??mico");

                        SetearDatos(guar, separ, kind, prim, opin, conduc, camb, rendi);

                    } else {
                        Toast.makeText(Editar_HistorialE.this, "El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Editar_HistorialE.this, "Error al consultar: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public void irAntecedentemedicos(View view){
        Intent i = new Intent(Editar_HistorialE.this, Editar_AntecedentesM.class);
        i.putExtra("idpa",idpaciente);
        startActivity(i);
    }

    public void irDatosGenerales (View view){
        Intent j = new Intent(Editar_HistorialE.this, Editar_DatosG.class);
        j.putExtra("idpa",idpaciente);
        startActivity(j);
    }

    public void registrar(){
        String guar = guarderia.getText().toString();
        String sep= separacion.getText().toString();
        String kind = kinder.getText().toString();
        String prim= primaria.getText().toString();
        String opin = opinion.getText().toString();
        String condu= conducta.getText().toString();
        String camb = cambios.getText().toString();
        String rend = rendimiento.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("Asisti?? a guarder??a", guar);
        map.put("Present?? angustia por separaci??n", sep);
        map.put("Edad en la que inici?? el kinder", kind);
        map.put("Edad en la que inici?? la primaria", prim);
        map.put("Opini??n de la escuela acerca del ni??o", opin);
        map.put("C??mo es su conducta escolar", condu);
        map.put("Cambios de escuela (motivos)", camb);
        map.put("Rendimiento acad??mico", rend);

        document.collection("datos").document("HistoriaEscolar").set(map);
        Toast.makeText(Editar_HistorialE.this, "Registro de la historia escolar exitoso ", Toast.LENGTH_SHORT).show();
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        guarderia= findViewById(R.id.editTextHEguarderiar_e);
        separacion = findViewById(R.id.editTextHEangustia_e);
        kinder= findViewById(R.id.editTextHEkinder_e);
        primaria= findViewById(R.id.editTextHEprimaria_e);
        opinion= findViewById(R.id.editTextTextHEopinionesc_e);
        conducta= findViewById(R.id.editTextHEconducta_e);
        cambios= findViewById(R.id.editTextHEcambiosesc_e);
        rendimiento= findViewById(R.id.editTextHErendimiento_e);
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