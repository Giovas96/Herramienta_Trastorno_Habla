package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Desarrollo;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_EvolucionM;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Habitos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Habitos extends AppCompatActivity {

    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText leche, mamila, chupon, dedo, alimentacion, dormir, sueno;
    ImageView back, save;
    DocumentReference document, editard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitos);
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        editard= document.collection("datos").document("Habitos");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Habitos.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        editard.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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
                    } else {
                        Toast.makeText(Habitos.this,"El documento no existe, por favor registra los datos", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Habitos.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void irDesarrollo(View view){
        Intent i = new Intent(Habitos.this, Desarrollo.class);
        i.putExtra("idpa",idpaciente);
        startActivity(i);
    }

    public void irevolucionmotriz (View view){
        Intent j = new Intent(Habitos.this, Evolucionmotriz.class);
        j.putExtra("idpa",idpaciente);
        startActivity(j);
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        leche=findViewById(R.id.editTextHleche);
        mamila=findViewById(R.id.editTextHmamila);
        chupon=findViewById(R.id.editTextHchupon);
        dedo=findViewById(R.id.editTextHdedo);
        alimentacion =findViewById(R.id.editTextHalimentacion);
        dormir=findViewById(R.id.editTextHdormir);
        sueno=findViewById(R.id.editTextHsueno);

    }

    public void registrar(){
        String lec = leche.getText().toString();
        String mam= mamila.getText().toString();
        String chu = chupon.getText().toString();
        String ded= dedo.getText().toString();
        String ali = alimentacion.getText().toString();
        String dor= dormir.getText().toString();
        String sue = sueno.getText().toString();


        Map<String, Object> map = new HashMap<>();
        map.put("Tomó leche materna ¿Cuánto tiempo?", lec);
        map.put("Tomó mamila ¿Cuánto tiempo?", mam);
        map.put("Usó chupón", chu);
        map.put("Se chupa el dedo", ded);
        map.put("Hubo problemas para alimentarlo", ali);
        map.put("Edad en la que durmio toda la noche", dor);
        map.put("¿Cómo fue su sueño en la noche?", sue);


        document.collection("datos").document("Habitos").set(map);
        Toast.makeText(Habitos.this, "Registro de los habitos exitoso ", Toast.LENGTH_SHORT).show();
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