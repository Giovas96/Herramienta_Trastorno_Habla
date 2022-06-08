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

import com.androidavanzado.herramienta_trastorno_habla.DatosGenerales;
import com.androidavanzado.herramienta_trastorno_habla.Desarrollo;
import com.androidavanzado.herramienta_trastorno_habla.HistoriaEscolar;
import com.androidavanzado.herramienta_trastorno_habla.Historialclinico;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Editar_DatosG extends AppCompatActivity {

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
        setContentView(R.layout.activity_editar__datos_g);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//vertical
        idpaciente= getIntent().getStringExtra("idpa");
        inicializarDatos();
        mAuth=FirebaseAuth.getInstance();
        nFirestore= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();

        document= nFirestore.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        datos=document.collection("datos").document("DatosGenerales");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Editar_DatosG.this, Historialclinico.class);
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

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_DatosG.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_DatosG.this, new DatePickerDialog.OnDateSetListener() {
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
                        Toast.makeText(Editar_DatosG.this,"El documento no existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Editar_DatosG.this,"Error al consultar: "+task.getException(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(Editar_DatosG.this, "Registro de los datos generales exitoso ", Toast.LENGTH_SHORT).show();
    }

    public void irHistoriaEscolar(View view){
        Intent i = new Intent(this, Editar_HistorialE.class);
        i.putExtra("idpa",idpaciente);
        startActivity(i);
    }

    public void irDesarrollo (View view){
        Intent j = new Intent(this, Editar_Desarrollo.class);
        j.putExtra("idpa",idpaciente);
        startActivity(j);
    }

    private void inicializarDatos(){
        back= findViewById(R.id.back_historial_secc);
        save= findViewById(R.id.save_historial);
        fecha = findViewById(R.id.editTextDGfecha_e);
        entrevistado=findViewById(R.id.editTextDGentrevistado_e);
        relacion= findViewById(R.id.editTextDGrelacion_e);
        referido= findViewById(R.id.editTextDGreferido_e);
        paciente =findViewById(R.id.editTextDGpaciente_e);
        nacimiento=findViewById(R.id.editTextDGnacimiento_e);
        fechanac= findViewById(R.id.editTextDGfechanac_e);
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