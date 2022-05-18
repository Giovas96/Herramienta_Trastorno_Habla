package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Desarrollo;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_Desarrollo;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_Desarrollo extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button datosg, habitos;
    FirebaseFirestore nFirestore;
    TextView previo,deseado, esperaban,movia,aborto, duracion, parto, incubadora, llanto, color, transfuciones, ictericia, apgar, peso, talla, forceps;
    ImageView back, edit;
    DocumentReference cdesarrollo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__desarrollo);
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        cdesarrollo = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("Desarrollo");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Desarrollo.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_Desarrollo.this, Editar_Desarrollo.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });

        cdesarrollo.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String prev = document.getString("Embarazo previo (incluir aborto)");
                        String dese = document.getString("Embarazo deseado");
                        String espe = document.getString("Esperaban");
                        String emba = document.getString("Duración del embarazo");
                        String movi = document.getString("El bebé se movía durante el embarazo");
                        String amen = document.getString("Amenaza o intento de aborto");
                        String part = document.getString("Parto");
                        String incu = document.getString("Incubadora");
                        String llant = document.getString("Llanto");
                        String colo = document.getString("Color");
                        String transfu = document.getString("Transfuciones");
                        String icteric = document.getString("Ictericia");
                        String apga = document.getString("Apgar");
                        String pesos = document.getString("Peso");
                        String tall = document.getString("Talla");
                        String force = document.getString("Forceps");

                        SetearDatos(prev,dese,espe,emba,movi,amen,part,incu,llant,colo,transfu,icteric,apga,pesos,tall,force);
                    } else {
                        Toast.makeText(Consultar_Desarrollo.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(Consultar_Desarrollo.this, Desarrollo.class));
                    }
                } else {
                    Toast.makeText(Consultar_Desarrollo.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        datosg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_Desarrollo.this, Consultar_DatosG.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        habitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_Desarrollo.this, Consultar_Habitos.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });

    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_conh);
        edit= findViewById(R.id.edit_conh);
        datosg= findViewById(R.id.butDatogD_c);
        habitos= findViewById(R.id.buthabiD_c);
        previo=findViewById(R.id.editTextDembarazo_c);
        deseado = findViewById(R.id.editTextDdeseado_c);
        esperaban = findViewById(R.id.editTextDesperaban_c);
        duracion=findViewById(R.id.editTexDduracion_c);
        movia=findViewById(R.id.editTextDMovia_c);
        aborto=findViewById(R.id.editTextDAborto_c);
        parto=findViewById(R.id.editTextDparto_c);
        incubadora=findViewById(R.id.editTextincubadora_c);
        llanto=findViewById(R.id.editTextDllanto_c);
        color=findViewById(R.id.editTextDcolor_c);
        transfuciones=findViewById(R.id.editTextDtransfuciones_c);
        ictericia=findViewById(R.id.editTextDictericia_c);
        apgar=findViewById(R.id.editTextDapgar_c);
        peso=findViewById(R.id.editTextDpeso_c);
        talla=findViewById(R.id.editTextDtalla_c);
        forceps=findViewById(R.id.editTextDforceps_c);
    }
    private void SetearDatos(String prev,String dese,String esper,String mov,String abor,String dur,String par,String inca,
                             String llan,String col,String trans,String icte,String apg,String pes,String tal,String forc){

        previo.setText(prev);
        deseado.setText(dese);
        esperaban.setText(esper);
        movia.setText(mov);
        aborto.setText(abor);
        duracion.setText(dur);
        parto.setText(par);
        incubadora.setText(inca);
        llanto.setText(llan);
        color.setText(col);
        transfuciones.setText(trans);
        ictericia.setText(icte);
        apgar.setText(apg);
        peso.setText(pes);
        talla.setText(tal);
        forceps.setText(forc);

    }
}