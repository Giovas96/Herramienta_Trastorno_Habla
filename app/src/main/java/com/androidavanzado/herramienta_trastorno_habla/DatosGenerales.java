package com.androidavanzado.herramienta_trastorno_habla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_DatosG;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DatosGenerales extends AppCompatActivity {

    String idpaciente, idprincipal;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    EditText fecha, entrevistado, relacion, referido, paciente, nacimiento, fechanac;
    ImageView back, save;
    int dia, mes, anio;
    DocumentReference document,datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        datos=document.collection("datos").document("DatosGenerales");


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DatosGenerales.this, Historialclinico.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(DatosGenerales.this, new DatePickerDialog.OnDateSetListener() {
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
                        fecha.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(DatosGenerales.this, new DatePickerDialog.OnDateSetListener() {
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
                        fechanac.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        datos.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String fec = document.getString("Fecha");
                        String entre = document.getString("Nombre del entrevistado");
                        String rela = document.getString("Relacion con el paciente");
                        String refe = document.getString("Referido por");
                        String paci = document.getString("Nombre del paciente");
                        String luga = document.getString("Lugar de nacimiento");
                        String fecnaci = document.getString("Fecha de nacimiento");


                        SetearDatos(fec,entre,rela,refe,paci,luga,fecnaci);
                    } else {
                        Toast.makeText(DatosGenerales.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DatosGenerales.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void registrar(){
        String fechas = fecha.getText().toString();
        String pacientes= paciente.getText().toString();
        String entrevistados = entrevistado.getText().toString();
        String relacions= relacion.getText().toString();
        String referidos = referido.getText().toString();
        String lugars= nacimiento.getText().toString();
        String fechanacs = fechanac.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("Fecha", fechas);
        map.put("Nombre del entrevistado", entrevistados);
        map.put("Relacion con el paciente", relacions);
        map.put("Referido por", referidos);
        map.put("Nombre del paciente", pacientes);
        map.put("Lugar de nacimiento", lugars);
        map.put("Fecha de nacimiento", fechanacs);

        document.collection("datos").document("DatosGenerales").set(map);
        Toast.makeText(DatosGenerales.this, "Registro de los datos generales exitoso ", Toast.LENGTH_SHORT).show();
    }

     public void irHistoriaEscolar(View view){
        registrar();
        Intent i = new Intent(this, HistoriaEscolar.class);
        startActivity(i);
    }

    public void irDesarrollo (View view){
        registrar();
        Intent j = new Intent(this, Desarrollo.class);
        startActivity(j);
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        fecha = findViewById(R.id.editTextDGfecha);
        entrevistado=findViewById(R.id.editTextDGentrevistado);
        relacion= findViewById(R.id.editTextDGrelacion);
        referido= findViewById(R.id.editTextDGreferido);
        paciente =findViewById(R.id.editTextDGpaciente);
        nacimiento=findViewById(R.id.editTextDGnacimiento);
        fechanac= findViewById(R.id.editTextDGfechanac);
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