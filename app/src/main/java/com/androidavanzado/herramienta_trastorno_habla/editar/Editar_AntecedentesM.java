package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Antecedentemedicos;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.Lenguaje;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_AntecedentesM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Editar_AntecedentesM extends AppCompatActivity {
    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText sarampion, varicela, paperas, influenza, asma, otras, hospitalizada;
    EditText pediatra, telpediatra, citaped, dentista, teldentist, citadent, psicologo, telpsi, citapsi;
    ImageView back, save;
    int dia, mes, anio;
    DocumentReference document,antecedentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__antecedentes_m);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        antecedentes= document.collection("datos").document("AntecedentesMedicos");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_AntecedentesM.this, Historialclinico.class);
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

        citaped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_AntecedentesM.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Anioseleccionado, int Messelecionado, int Diaselecionado) {
                        String diaformateado, mesformateado;
                        //Obtener dia
                        if(Diaselecionado < 10){
                            diaformateado='0'+ String.valueOf(Diaselecionado);
                        }else{
                            diaformateado= String.valueOf(Diaselecionado);
                        }
                        //Obtener mes
                        int Mes = Messelecionado+1;
                        if(Mes<10){
                            mesformateado= '0'+ String.valueOf(Mes);
                        }else{
                            mesformateado= String.valueOf(Mes);
                        }
                        citaped.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        citadent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_AntecedentesM.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Anioseleccionado, int Messelecionado, int Diaselecionado) {
                        String diaformateado, mesformateado;
                        //Obtener dia
                        if(Diaselecionado < 10){
                            diaformateado='0'+ String.valueOf(Diaselecionado);
                        }else{
                            diaformateado= String.valueOf(Diaselecionado);
                        }
                        //Obtener mes
                        int Mes = Messelecionado+1;
                        if(Mes<10){
                            mesformateado= '0'+ String.valueOf(Mes);
                        }else{
                            mesformateado= String.valueOf(Mes);
                        }
                        citadent.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        citapsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_AntecedentesM.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Anioseleccionado, int Messelecionado, int Diaselecionado) {
                        String diaformateado, mesformateado;
                        //Obtener dia
                        if(Diaselecionado < 10){
                            diaformateado='0'+ String.valueOf(Diaselecionado);
                        }else{
                            diaformateado= String.valueOf(Diaselecionado);
                        }
                        //Obtener mes
                        int Mes = Messelecionado+1;
                        if(Mes<10){
                            mesformateado= '0'+ String.valueOf(Mes);
                        }else{
                            mesformateado= String.valueOf(Mes);
                        }
                        citapsi.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
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
                        Toast.makeText(Editar_AntecedentesM.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Editar_AntecedentesM.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    public void irLenguaje(View view){
        Intent i = new Intent(this, Editar_Lenguaje.class);
        i.putExtra("idpa", idpaciente);
        startActivity(i);
    }

    public void irHistoriaEscolar (View view){
        Intent j = new Intent(this, Editar_HistorialE.class);
        j.putExtra("idpa", idpaciente);
        startActivity(j);
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        sarampion=findViewById(R.id.editTextAMsarampion_e);
        varicela=findViewById(R.id.editTextAMvaricela_e);
        paperas=findViewById(R.id.editTextAMpaperas_e);
        influenza=findViewById(R.id.editTextAMinfluenza_e);
        asma =findViewById(R.id.editTextAMasma_e);
        otras=findViewById(R.id.editTextAMotras_e);
        hospitalizada=findViewById(R.id.editTextAMhospitalizado_e);
        pediatra=findViewById(R.id.editTextAMpediatra_e);
        telpediatra=findViewById(R.id.editTextAMtelped_e);
        citaped=findViewById(R.id.editTextAMcitaped_e);
        dentista=findViewById(R.id.editTextAMdentista_e);
        teldentist=findViewById(R.id.editTextAMteldent_e);
        citadent=findViewById(R.id.editTextAMcitadent_e);
        psicologo=findViewById(R.id.editTextAMpsicologo_e);
        telpsi=findViewById(R.id.editTextAMtelpsico_e);
        citapsi=findViewById(R.id.editTextAMcitapsico_e);
    }

    public void registrar(){
        String saram = sarampion.getText().toString();
        String varic= varicela.getText().toString();
        String paper = paperas.getText().toString();
        String influenz= influenza.getText().toString();
        String asm = asma.getText().toString();
        String otrs= otras.getText().toString();
        String hospi = hospitalizada.getText().toString();
        String ped = pediatra.getText().toString();
        String tped = telpediatra.getText().toString();
        String cped = citaped.getText().toString();
        String den = dentista.getText().toString();
        String tden = teldentist.getText().toString();
        String cden = citadent.getText().toString();
        String psi = psicologo.getText().toString();
        String tpsi = telpsi.getText().toString();
        String cpsi = citapsi.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("Sarampión", saram);
        map.put("Varicela", varic);
        map.put("Paperas", paper);
        map.put("Influenza", influenz);
        map.put("Asma", asm);
        map.put("Otras", otrs);
        map.put("Ha sido hospitalizado (por qué)", hospi);
        map.put("Pediatra", ped);
        map.put("Telefono", tped);
        map.put("Fecha de la ultima cita", cped);
        map.put("Dentista", den);
        map.put("Telefono", tden);
        map.put("Fecha de la ultima cita", cden);
        map.put("Psicologo", psi);
        map.put("Telefono", tpsi);
        map.put("Fecha de la ultima cita", cpsi);

        document.collection("datos").document("AntecedentesMedicos").set(map);
        Toast.makeText(Editar_AntecedentesM.this, "Registro de los antecedentes medicos exitoso ", Toast.LENGTH_SHORT).show();
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