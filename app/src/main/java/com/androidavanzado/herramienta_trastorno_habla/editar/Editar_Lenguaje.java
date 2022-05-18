package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Antecedentemedicos;
import com.androidavanzado.herramienta_trastorno_habla.Evolucionmotriz;
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

public class Editar_Lenguaje extends AppCompatActivity {

    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText balbuceo, primeras, juntas, corrido, pronunciar, defecto,entiende, tartamudeo, mimiza;
    ImageView back, save;
    DocumentReference document,elenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__lenguaje);
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
                Intent i = new Intent(Editar_Lenguaje.this, Historialclinico.class);
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
                        Toast.makeText(Editar_Lenguaje.this,"El documento no existe", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Editar_Lenguaje.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void irEvolucionmotriz(View view){
        Intent i = new Intent(this, Editar_EvolucionM.class);
        i.putExtra("idpa",idpaciente);
        startActivity(i);
    }

    public void irAntecentemedicos (View view){
        Intent j = new Intent(this, Editar_AntecedentesM.class);
        j.putExtra("idpa",idpaciente);
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
        Toast.makeText(Editar_Lenguaje.this, "Registro del lenguaje exitoso ", Toast.LENGTH_SHORT).show();
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        balbuceo = findViewById(R.id.editTextLbalbuceo_e);
        primeras = findViewById(R.id.editTextLpalabras_e);
        juntas = findViewById(R.id.editTextLdpalabras_e);
        corrido = findViewById(R.id.editTextLcorrido_e);
        pronunciar = findViewById(R.id.editTextLsonido_e);
        defecto = findViewById(R.id.editTextLdefecto_e);
        entiende = findViewById(R.id.editTextLentender_e);
        tartamudeo = findViewById(R.id.editTextLtartamudea_e);
        mimiza = findViewById(R.id.editTextLmimiza_e);
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