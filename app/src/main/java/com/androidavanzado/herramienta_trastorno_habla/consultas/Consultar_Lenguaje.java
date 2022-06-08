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

import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.Lenguaje;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_HistorialE;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Lenguaje;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Consultar_Lenguaje extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button evolucionm, antecedentesm;
    FirebaseFirestore nFirestore;
    TextView balbuceo, primeras, juntas, corrido, pronunciar, defecto,entiende, tartamudeo, mimiza;
    ImageView back, edit;
    DocumentReference clenguaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__lenguaje);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        clenguaje = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("Lenguaje");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Lenguaje.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_Lenguaje.this, Editar_Lenguaje.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });

        clenguaje.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

                        antecedentesm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(Consultar_Lenguaje.this, Consultar_AntecedentesM.class);
                                i.putExtra("idpa", idpaciente);
                                startActivity(i);
                            }
                        });
                        evolucionm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent j = new Intent(Consultar_Lenguaje.this, Consultar_EvolucionM.class);
                                j.putExtra("idpa", idpaciente);
                                startActivity(j);
                            }
                        });
                    } else {
                        Toast.makeText(Consultar_Lenguaje.this,"El documento no existe", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Consultar_Lenguaje.this, Lenguaje.class));
                    }
                } else {
                    Toast.makeText(Consultar_Lenguaje.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        evolucionm=findViewById(R.id.butEvolmL_c);
        antecedentesm=findViewById(R.id.butAntL_c);
        balbuceo = findViewById(R.id.editTextLbalbuceo_c);
        primeras = findViewById(R.id.editTextLpalabras_c);
        juntas = findViewById(R.id.editTextLdpalabras_c);
        corrido = findViewById(R.id.editTextLcorrido_c);
        pronunciar = findViewById(R.id.editTextLsonido_c);
        defecto = findViewById(R.id.editTextLdefecto_c);
        entiende = findViewById(R.id.editTextLentender_c);
        tartamudeo = findViewById(R.id.editTextLtartamudea_c);
        mimiza = findViewById(R.id.editTextLmimiza_c);
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