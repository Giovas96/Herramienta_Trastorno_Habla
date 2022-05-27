package com.androidavanzado.herramienta_trastorno_habla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.UiAutomation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroTutor extends AppCompatActivity {

    ImageView save, back;
    EditText Nombre, Apellidopat, Apellidomat, Fechanac, celular, casa, profesion,
            Nombred, Apellidopatd, Apellidomatd, Fechanacd, celulard, casad, profesiond ;
    int dia, mes, anio;
    FirebaseAuth mAuth;
    FirebaseFirestore terapeuta;
    String idprincipal, idpaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_tutor);
        idpaciente= getIntent().getStringExtra("Pid");
        Inicializarpaciente();
        mAuth=FirebaseAuth.getInstance();
        terapeuta= FirebaseFirestore.getInstance();
        idprincipal = mAuth.getCurrentUser().getUid();
        DocumentReference documentpaciente = terapeuta.collection("terapeutas").document(idprincipal).collection("paciente").document(idpaciente);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistroTutor.this, RegistroPaciente.class);
                startActivity(i);
            }
        });

        Fechanac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendario= Calendar.getInstance();
                dia = calendario.get(Calendar.DAY_OF_MONTH);
                mes = calendario.get(Calendar.MONTH);
                anio= calendario.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog= new DatePickerDialog(RegistroTutor.this, new DatePickerDialog.OnDateSetListener() {
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

                DatePickerDialog datePickerDialog= new DatePickerDialog(RegistroTutor.this, new DatePickerDialog.OnDateSetListener() {
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

                    documentpaciente.collection("datos").document("tutorp").set(map);
                    Toast.makeText(RegistroTutor.this, "Registro exitoso de un tutor", Toast.LENGTH_SHORT).show();
                  if (!nombretd.isEmpty() && !apellidoptd.isEmpty() && !apellidomtd.isEmpty() && !fechanactd.isEmpty() && !celtd.isEmpty() &&
                            !casatd.isEmpty() && !proftd.isEmpty()){

                      Map<String, Object> mapd = new HashMap<>();
                      mapd.put("Nombre(s)", nombretd);
                      mapd.put("Apellido Paterno", apellidoptd);
                      mapd.put("Apellido Materno", apellidomtd);
                      mapd.put("Fecha de nacimiento", fechanactd);
                      mapd.put("Celular", celtd);
                      mapd.put("Casa", casatd);
                      mapd.put("Profesión", proftd);

                      documentpaciente.collection("datos").document("tutorpd").set(mapd);
                      Toast.makeText(RegistroTutor.this, "Registro exitoso de ambos tutores", Toast.LENGTH_SHORT).show();
                    }
                    Intent i = new Intent(RegistroTutor.this, Listarpacientes.class);
                    startActivity(i);
                }else{
                    Toast.makeText(RegistroTutor.this, "Registro no exitoso", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    private void Inicializarpaciente(){

        save=findViewById(R.id.save_tut);
        back = findViewById(R.id.back_regist);
        Nombre  = findViewById(R.id.editTextnombretut);
        Apellidopat = findViewById(R.id.editTextapellidoptup);
        Apellidomat= findViewById(R.id.editTextapellidomtu);
        Fechanac= findViewById(R.id.editTextfechatut);
        celular= findViewById(R.id.editTextceltut);
        casa = findViewById(R.id.editTextcasatut);
        profesion= findViewById(R.id.editTextproftut);
        Nombred = findViewById(R.id.editTextnombtut2);
        Apellidopatd = findViewById(R.id.editTextapellidoptu2);
        Apellidomatd = findViewById(R.id.editTextapellidomtu2);
        Fechanacd = findViewById(R.id.editTextfechatut2);
        celulard = findViewById(R.id.editTextceltut2);
        casad = findViewById(R.id.editTextcasatut2);
        profesiond = findViewById(R.id.editTextproftut2);

    }

}