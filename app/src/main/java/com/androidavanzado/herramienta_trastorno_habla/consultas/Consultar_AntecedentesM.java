package com.androidavanzado.herramienta_trastorno_habla.consultas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Antecedentemedicos;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_AntecedentesM;
import com.androidavanzado.herramienta_trastorno_habla.editar.Editar_EvolucionM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Consultar_AntecedentesM extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    Button lenguaje, historiae;
    FirebaseFirestore nFirestore;
    TextView sarampion, varicela, paperas, influenza, asma, otras, hospitalizada;
    TextView pediatra, telpediatra, citaped, dentista, teldentist, citadent, psicologo, telpsi, citapsi;
    ImageView back, edit;
    DocumentReference antecedentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__antecedentes_m);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente = getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        antecedentes = nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("AntecedentesMedicos");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_AntecedentesM.this, Historialclinico.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_AntecedentesM.this, Editar_AntecedentesM.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });

        antecedentes.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String sar = document.getString("Sarampión");
                        String var = document.getString("Varicela");
                        String pap = document.getString("Paperas");
                        String inf = document.getString("Influenza");
                        String as = document.getString("Asma");
                        String otr = document.getString("Otras");
                        String hos = document.getString("Ha sido hospitalizado (por qué)");
                        String ped = document.getString("Pediatra");
                        String tped = document.getString("Telefono");
                        String cped = document.getString("Fecha de la ultima cita");
                        String den = document.getString("Dentista");
                        String tden = document.getString("Telefono");
                        String cden = document.getString("Fecha de la ultima cita");
                        String psi = document.getString("Psicologo");
                        String tpsi = document.getString("Telefono");
                        String cpsi = document.getString("Fecha de la ultima cita");

                        SetearDatos(sar,var,pap,inf,as,otr,hos,ped,tped,cped,den,tden,cden,psi,tpsi,cpsi);
                    } else {
                        Toast.makeText(Consultar_AntecedentesM.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Consultar_AntecedentesM.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        lenguaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Consultar_AntecedentesM.this, Consultar_Lenguaje.class);
                i.putExtra("idpa", idpaciente);
                startActivity(i);
            }
        });

        historiae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(Consultar_AntecedentesM.this, Consultar_HistorialE.class);
                j.putExtra("idpa", idpaciente);
                startActivity(j);
            }
        });


    }

    private void inicializarDatos() {
        back = findViewById(R.id.back_conh);
        edit = findViewById(R.id.edit_conh);
        lenguaje=findViewById(R.id.butLengAM_c);
        historiae=findViewById(R.id.butHistorAM_c);
        sarampion = findViewById(R.id.editTextAMsarampion_c);
        varicela = findViewById(R.id.editTextAMvaricela_c);
        paperas = findViewById(R.id.editTextAMpaperas_c);
        influenza = findViewById(R.id.editTextAMinfluenza_c);
        asma = findViewById(R.id.editTextAMasma_c);
        otras = findViewById(R.id.editTextAMotras_c);
        hospitalizada = findViewById(R.id.editTextAMhospitalizado_c);
        pediatra = findViewById(R.id.editTextAMpediatra_c);
        telpediatra = findViewById(R.id.editTextAMtelped_c);
        citaped = findViewById(R.id.editTextAMcitaped_c);
        dentista = findViewById(R.id.editTextAMdentista_c);
        teldentist = findViewById(R.id.editTextAMteldent_c);
        citadent = findViewById(R.id.editTextAMcitadent_c);
        psicologo = findViewById(R.id.editTextAMpsicologo_c);
        telpsi = findViewById(R.id.editTextAMtelpsico_c);
        citapsi = findViewById(R.id.editTextAMcitapsico_c);
    }

    private void SetearDatos(String saram,String vari,String pape,String influ,String asm,String otr,String hospi,
                             String pedi,String telpe,String citpe,String dent,String telde,String citden,String psic,String telpsico,String citpsico){

        sarampion.setText(saram);
        varicela.setText(vari);
        paperas.setText(pape);
        influenza.setText(influ);
        asma.setText(asm);
        otras.setText(otr);
        hospitalizada.setText(hospi);
        pediatra.setText(pedi);
        telpediatra.setText(telpe);
        citaped.setText(citpe);
        dentista.setText(dent);
        teldentist.setText(telde);
        citadent.setText(citden);
        psicologo.setText(psic);
        telpsi.setText(telpsico);
        citapsi.setText(citpsico);

    }


}