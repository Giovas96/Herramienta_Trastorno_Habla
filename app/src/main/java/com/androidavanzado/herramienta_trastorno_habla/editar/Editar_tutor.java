package com.androidavanzado.herramienta_trastorno_habla.editar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidavanzado.herramienta_trastorno_habla.Listarpacientes;
import com.androidavanzado.herramienta_trastorno_habla.R;
import com.androidavanzado.herramienta_trastorno_habla.RegistroTutor;
import com.androidavanzado.herramienta_trastorno_habla.consultas.Consultar_tutor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Editar_tutor extends AppCompatActivity {
    ImageView back, save;
    EditText Nombre, Apellidopat, Apellidomat, Fechanac, celular, casa, profesion,
            Nombred, Apellidopatd, Apellidomatd, Fechanacd, celulard, casad, profesiond ;
    DocumentReference tutor,tutord;
    FirebaseAuth mAuth;
    int dia, mes, anio;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tutor);
        idpaciente= getIntent().getStringExtra("idp");
        Inicializarpaciente();
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        tutor = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("tutorp");
        tutord= terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente).collection("datos").document("tutorpd");

        Fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_tutor.this, new DatePickerDialog.OnDateSetListener() {
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
                        Fechanac.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        Fechanacd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(Editar_tutor.this, new DatePickerDialog.OnDateSetListener() {
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
                        Fechanacd.setText(diaformateado + '/' + mesformateado + '/' + Anioseleccionado);
                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        tutor.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String nom= document.getString("Nombre()s");
                        String app= document.getString("Apellido Paterno");
                        String apm= document.getString("Apellido Materno");
                        String nac= document.getString("Fecha de nacimiento");
                        String celu= document.getString("Celular");
                        String home= document.getString("Casa");
                        String pro= document.getString("Profesión");

                        SetearDatos(nom,app,apm,nac,celu,home,pro);
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        tutord.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String nomd= document.getString("Nombre()s");
                        String appd= document.getString("Apellido Paterno");
                        String apmd= document.getString("Apellido Materno");
                        String nacd= document.getString("Fecha de nacimiento");
                        String celud= document.getString("Celular");
                        String homed= document.getString("Casa");
                        String prod= document.getString("Profesión");

                        SetearDatosd(nomd,appd,apmd,nacd,celud,homed,prod);
                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombret = Nombre.getText().toString();
                String apellidopt= Apellidopat.getText().toString();
                String apellidomt = Apellidomat.getText().toString();
                String fechanact= Fechanac.getText().toString();
                String celt = celular.getText().toString();
                String casat= casa.getText().toString();
                String proft = profesion.getText().toString();
                String nombretd= Nombred.getText().toString();
                String apellidoptd= Apellidopatd.getText().toString();
                String apellidomtd= Apellidomatd.getText().toString();
                String fechanactd = Fechanacd.getText().toString();
                String celtd= celulard.getText().toString();
                String casatd = casad.getText().toString();
                String proftd= profesiond.getText().toString();
                if (!nombret.isEmpty() && !apellidopt.isEmpty() && !apellidomt.isEmpty() && !fechanact.isEmpty() && !celt.isEmpty() &&
                        !casat.isEmpty() && !proft.isEmpty()){
                    Map<String, Object> map = new HashMap<>();
                    map.put("Nombre()s", nombret);
                    map.put("Apellido Paterno", apellidopt);
                    map.put("Apellido Materno", apellidomt);
                    map.put("Fecha de nacimiento", fechanact);
                    map.put("Celular", celt);
                    map.put("Casa", casat);
                    map.put("Profesión", proft);
                    tutor.set(map);
                    Toast.makeText(Editar_tutor.this, "Edicion exitoso de un tutor", Toast.LENGTH_SHORT).show();
                    if (!nombretd.isEmpty() && !apellidoptd.isEmpty() && !apellidomtd.isEmpty() && !fechanactd.isEmpty() && !celtd.isEmpty() &&
                            !casatd.isEmpty() && !proftd.isEmpty()){
                        Map<String, Object> mapd = new HashMap<>();
                        mapd.put("Nombre()s", nombretd);
                        mapd.put("Apellido Paterno", apellidoptd);
                        mapd.put("Apellido Materno", apellidomtd);
                        mapd.put("Fecha de nacimiento", fechanactd);
                        mapd.put("Celular", celtd);
                        mapd.put("Casa", casatd);
                        mapd.put("Profesión", proftd);
                        tutord.set(mapd);
                        Toast.makeText(Editar_tutor.this, "Edición exitoso de ambos tutores", Toast.LENGTH_SHORT).show();
                    }
                    Intent i = new Intent(Editar_tutor.this, Consultar_tutor.class);
                    i.putExtra("idp",idpaciente);
                    startActivity(i);
                }else{
                    Toast.makeText(Editar_tutor.this, "Edición no exitosa", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Inicializarpaciente(){

        back=findViewById(R.id.back_edittut);
        save = findViewById(R.id.save_editut);
        Nombre  = findViewById(R.id.editTextnombretut_e);
        Apellidopat = findViewById(R.id.editTextapellidoptup_e);
        Apellidomat= findViewById(R.id.editTextapellidomtu_e);
        Fechanac= findViewById(R.id.editTextfechatut_e);
        celular= findViewById(R.id.editTextceltut_e);
        casa = findViewById(R.id.editTextcasatut_e);
        profesion= findViewById(R.id.editTextproftut_e);
        Nombred = findViewById(R.id.editTextnombtut2_e);
        Apellidopatd = findViewById(R.id.editTextapellidoptu2_e);
        Apellidomatd = findViewById(R.id.editTextapellidomtu2_e);
        Fechanacd = findViewById(R.id.editTextfechatut2_e);
        celulard = findViewById(R.id.editTextceltut2_e);
        casad = findViewById(R.id.editTextcasatut2_e);
        profesiond = findViewById(R.id.editTextproftut2_e);

    }

    private void SetearDatos(String nombre,String apellidopat,String apellidomat,String fechanac,String cel,String cas,String prof){

        Nombre.setText(nombre);
        Apellidopat.setText(apellidopat);
        Apellidomat.setText(apellidomat);
        Fechanac.setText(fechanac);
        celular.setText(cel);
        casa.setText(cas);
        profesion.setText(prof);
    }

    private void SetearDatosd(String nombre,String apellidopat,String apellidomat,String fechanac,String cel,String cas,String prof){

        Nombred.setText(nombre);
        Apellidopatd.setText(apellidopat);
        Apellidomatd.setText(apellidomat);
        Fechanacd.setText(fechanac);
        celulard.setText(cel);
        casad.setText(cas);
        profesiond.setText(prof);
    }
}