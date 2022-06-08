package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_AntecedentesM;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_EvolucionM;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_Lenguaje;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Lenguaje extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText balbuceo, primeras, juntas, corrido, pronunciar, defecto,entiende, tartamudeo, mimiza;
    ImageView back, save;
    DocumentReference document,elenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenguaje);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        elenguaje= document.collection("datos").document("Lenguaje");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Lenguaje.this, Historialclinico.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        elenguaje.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String balb = document.getString("Balbuceó a los");
                        String prim= document.getString("Primeras palabras");
                        String jun = document.getString("Dos palabras juntas");
                        String cor= document.getString("Habló de corrido");
                        String pron = document.getString("Dificultad para pronunciar algún sonido (edad)");
                        String def= document.getString("Algún otro defecto en su lenguaje");
                        String ent = document.getString("Entiende cuando se le hable");
                        String tart = document.getString("Tartamudea");
                        String mimi = document.getString("Utiliza mímiza para comunicarse");

                        SetearDatos(balb,prim,jun,cor,pron,def,ent,tart,mimi);

                    } else {
                        Toast.makeText(Lenguaje.this,"El documento no existe", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Lenguaje.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void irEvolucionmotriz(View view){
        Intent i = new Intent(this, Evolucionmotriz.class);
        startActivity(i);
    }

    public void irAntecentemedicos (View view){
        Intent j = new Intent(this, Antecedentemedicos.class);
        startActivity(j);
    }

    public void registrar(){
        String balb = balbuceo.getText().toString();
        String prim= primeras.getText().toString();
        String jun = juntas.getText().toString();
        String cor= corrido.getText().toString();
        String pron = pronunciar.getText().toString();
        String def= defecto.getText().toString();
        String ent = entiende.getText().toString();
        String tart = tartamudeo.getText().toString();
        String mimi = mimiza.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("Balbuceó a los", balb);
        map.put("Primeras palabras", prim);
        map.put("Dos palabras juntas", jun);
        map.put("Habló de corrido", cor);
        map.put("Dificultad para pronunciar algún sonido (edad)", pron);
        map.put("Algún otro defecto en su lenguaje", def);
        map.put("Entiende cuando se le hable", ent);
        map.put("Tartamudea", tart);
        map.put("Utiliza mímiza para comunicarse", mimi);


        document.collection("datos").document("Lenguaje").set(map);
        Toast.makeText(Lenguaje.this, "Registro del lenguaje exitoso ", Toast.LENGTH_SHORT).show();
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        balbuceo = findViewById(R.id.editTextLbalbuceo);
        primeras = findViewById(R.id.editTextLpalabras);
        juntas = findViewById(R.id.editTextLdpalabras);
        corrido = findViewById(R.id.editTextLcorrido);
        pronunciar = findViewById(R.id.editTextLsonido);
        defecto = findViewById(R.id.editTextLdefecto);
        entiende = findViewById(R.id.editTextLentender);
        tartamudeo = findViewById(R.id.editTextLtartamudea);
        mimiza = findViewById(R.id.editTextLmimiza);
    }
    private void SetearDatos(String bal,String pri,String jun,String cor,String pron,String def,String ent,String tart,String mim){
        balbuceo.setText(bal);
        primeras.setText(pri);
        juntas.setText(jun);
        corrido.setText(cor);
        pronunciar.setText(pron);
        defecto.setText(def);
        entiende.setText(ent);
        tartamudeo.setText(tart);
        mimiza.setText(mim);
    }
}